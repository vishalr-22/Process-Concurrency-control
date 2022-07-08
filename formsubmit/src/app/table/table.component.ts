import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatSort, Sort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { HttpClient } from '@angular/common/http';
import { MatButton } from '@angular/material/button';
import { NgModule } from '@angular/core';
import { User } from '../user';
import { DatePipe } from '@angular/common';
import { InteractionService } from '../interaction.service';
import { map } from 'rxjs/operators';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';



@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.css']
})
export class TableComponent implements OnInit {
  constructor(private http: HttpClient, public datepipe: DatePipe, private _interactionService: InteractionService) { }

  // mat progress bar
  isLoading = false;
  // loading table data
  Element_data = [];
  dataSource = new MatTableDataSource(this.Element_data);
  // columns that are displayed
  displayedColumns: string[] = ["id", "process", "inputFile", "outputFile", "processing", "processed", "createdBy",
    "createdAt", "updatedAt", "processStartedAt", "processFinishedAt", "run"];

  // paginator size options
  pageSizeOptions: number[] = [5, 10, 25, 100];

  // paginator page size option
  pageSize = 10;

  // paginator current page
  currentPage = 0;

  run = "run"
  column = "run"

  // web socket
  webSocketEndPoint: string = 'http://10.0.0.108:8093/ws';
  topic: string = "/topic/greetings";
  stompClient: any;

  _connect() {
    console.log("Initialize WebSocket Connection");
    let ws = new SockJS(this.webSocketEndPoint);
    this.stompClient = Stomp.over(ws);
    const _this = this;
    _this.stompClient.connect({}, function (frame: any) {
      _this.stompClient.subscribe(_this.topic, function (sdkEvent: any) {
        _this.onMessageReceived(sdkEvent);
      });
      _this.stompClient.reconnect_delay = 2000;
    }, this.errorCallBack);
  };

  _disconnect() {
    if (this.stompClient !== null) {
      this.stompClient.disconnect();
    }
    console.log("Disconnected");
  }

  // on error, schedule a reconnection attempt
  errorCallBack(error: any) {
    console.log("errorCallBack -> " + error)
    setTimeout(() => {
      this._connect();
    }, 5000);
  }

  /**
   * Send message to sever via web socket
   * @param {*} message 
   */
  _send(message: any) {
    console.log("calling logout api via web socket");
    this.stompClient.send("/app/hello", {}, JSON.stringify(message));
  }

  onMessageReceived(message: any) {
    console.log("Message Recieved from Server :: " + message);
    this._interactionService.sendMessageTableprocess(JSON.stringify(message.body))
    this.handleMessage(JSON.stringify(message.body));
  }

  webSocketAPI: any;
  greeting: any;
  name: any;

  connect() {
    this._connect();
  }

  disconnect() {
    this._disconnect();
  }

  sendMessage() {
    this._send(this.name);
  }

  handleMessage(message: any) {
    this.greeting = message;
  }

  @ViewChild('paginator') paginator!: MatPaginator;
  @ViewChild('empTbSort') empTbSort = new MatSort();

  ngOnInit(): void {
    this.loadData()

  }

  // paginator page changed
  pageChanged(event: PageEvent) {
    console.log({ event });
    this.pageSize = event.pageSize;
    this.currentPage = event.pageIndex;
    // this.loadData() 
  }

  // loading of data
  loadData() {
    this.isLoading = true;
    var URL = `http://10.0.0.108:8093/fetchUsers`;

    this.http.get(URL).subscribe((response) => {
      this.Element_data = JSON.parse(JSON.stringify(response["content"]))

      console.log(this.Element_data);
      this.dataSource = new MatTableDataSource(this.Element_data);
      // this.empTbSort.disableClear = true;
      // this.dataSource.sort = this.empTbSort;
      this.dataSource.paginator = this.paginator;
      setTimeout(() => {
        this.paginator.pageIndex = this.currentPage;
        this.paginator.length = response["totalElements"];
      });
      this.isLoading = false;

    }
    );
  }

  selectedProcessType: any
  id: any

  // run a process
  processStartedAt: any
  async runProcess(obj: any) {
    console.log(obj["id"])
    this.id = obj["id"]
    this.selectedProcessType = obj["process"]
    console.log(this.id);
    console.log(this.selectedProcessType);
    const toBeProcessed = true
    const updatedAt = this.datepipe.transform((new Date), 'MM/dd/yyyy h:mm:ss');

    var URL = `http://10.0.0.108:8093/startProcess?id=${this.id}&toBeProcessed=${toBeProcessed}&updatedAt=${updatedAt}`
    await this.http.get(URL).subscribe((response) => {
      this.loadData()
      this.addProcessTable(updatedAt)
    }
    );

  }

  async addProcessTable(processTime: any) {

    console.log("AddProcess")
    var URL = `http://10.0.0.108:8093/addProcess?processId=${this.id}&processTime=${processTime}`
    await this.http.get(URL).subscribe((response) => {
      this.id = null
      this.selectedProcessType = null
      this.processStartedAt = null
      this._interactionService.sendMessageTableprocess("Reload Now")
      // fetch all processes
      this.fetchAllProcesses()
    }
    );
  }

  async fetchAllProcesses() {

    console.log("fetchAllProcess")
    var URL = `http://10.0.0.108:8093/fetchProcesses`
    await this.http.get(URL).subscribe(async (response) => {
      ``
      console.log(response)
      // this.Element_data = JSON.parse(JSON.stringify(response))
      this.processQueue = JSON.parse(JSON.stringify(response)).length
      if (JSON.parse(JSON.stringify(response)).length == 1) {
        this.startActualProcess(response[0]["processId"], JSON.parse(JSON.stringify(response)).length, response[0]["id"])
      }
      else if (JSON.parse(JSON.stringify(response)).length > 1) {
        console.log(JSON.parse(JSON.stringify(response)))
        console.log(response[0]["processId"])
        var k: any
        // k = await this.findById(response[0]["processId"])
        console.log("findById")
        var URL = `http://10.0.0.108:8093/findById?id=${response[0]["processId"]}`

        var x
        await this.http.get(URL).subscribe((res) => {
          console.log(res)
          x = JSON.parse(JSON.stringify(res))
          k = x
          console.log(k)
          try {
            if (k["processing"] == false) {
              this.startActualProcess(response[0]["processId"], JSON.parse(JSON.stringify(response)).length, response[0]["id"])
            }
          }
          catch {

          }
        }
        );

      }
      else {
        console.log("Process Ended")
      }
    }
    );

  }

  async findById(processId: any) {

    console.log("findById")
    var URL = `http://10.0.0.108:8093/findById?id=${processId}`

    var x
    await this.http.get(URL).subscribe((response) => {
      console.log(response)
      x = JSON.parse(JSON.stringify(response))
    }
    );
    console.log(x)
    return x

  }

  processQueue = 0

  async startActualProcess(processId: any, length: any, processObjId: any) {

    const processing = true
    const processStartedAt = this.datepipe.transform((new Date), 'MM/dd/yyyy h:mm:ss');
    const toBeProcessed = false
    console.log("startActualProcess")
    var URL = `http://10.0.0.108:8093/startActualProcess?id=${processId}&processing=${processing}&processStartedAt=${processStartedAt}
      &toBeProcessed=${toBeProcessed}`
    await this.http.get(URL).subscribe((response) => {
      console.log(response)
      this.loadData()
      this.runActualProcess(processId, length, processObjId)
    }
    );

  }


  async runActualProcess(processId: any, length: any, processObjId: any) {

    // if (length == 1) {
    const processing = true
    const processStartedAt = this.datepipe.transform((new Date), 'MM/dd/yyyy h:mm:ss');
    const toBeProcessed = false
    console.log("runActualProcess")
    var URL = `http://10.0.0.108:8093/runProcess?id=${processId}&processing=${processing}&processStartedAt=${processStartedAt}
      &toBeProcessed=${toBeProcessed}`
    await this.http.get(URL).subscribe((response) => {
      console.log(response)
      this.loadData()
      this.removeProcess(processObjId)
      //delay
      this.finishProcess(processId)
    }
    );
    // else if (length > 1) {
    //   const processing = true
    //   const processStartedAt = this.datepipe.transform((new Date), 'MM/dd/yyyy h:mm:ss');
    //   const toBeProcessed = false
    //   console.log("runActualProcess")
    //   var URL = `http://10.0.0.108:8093/runProcess?id=${processId}&processing=${processing}&processStartedAt=${processStartedAt}
    //   &toBeProcessed=${toBeProcessed}`

    //   await this.http.get(URL).subscribe((response) => {
    //     console.log(response)
    //     this.loadData()
    //     this.removeProcess(processObjId)
    //     // delay
    //     this.finishProcess(processId)
    //     this.fetchAllProcesses()
    //   }
    //   );
    // }
  }

  async finishProcess(processId: any) {
    const processing = false
    const processed = true
    const processFinishedAt = this.datepipe.transform((new Date), 'MM/dd/yyyy h:mm:ss');
    console.log("finishProcess")
    var URL = `http://10.0.0.108:8093/finishProcess?id=${processId}&processing=${processing}&processFinishedAt=${processFinishedAt}&processed=${processed}`

    await this.http.get(URL).subscribe((response) => {
      console.log(response)
      this.loadData()
    }
    );
  }
  async removeProcess(id: any) {

    console.log("removeProcess")
    this.processQueue = this.processQueue - 1
    var URL = `http://10.0.0.108:8093/removeProcess?id=${id}`
    await this.http.get(URL).subscribe(() => {
      console.log("record deleted")
      this._interactionService.sendMessageTableprocess("Reload Now")
      // this.fetchAllProcesses()
      this.fetchAllProcesses()
    }
    );

  }

  addformpopup = false
  // add events, add data to the table
  addEvent() {
    this.addformpopup = true
  }

  closeAddPopUp() {
    this.addformpopup = false
  }
  addUser(response: any) {
    this.addformpopup = false
    console.log(response.value)
    const process = null
    const inputFile = response.value["inputFile"]
    const outputFile = response.value["outputFile"]
    const processing = false
    const toBeProcessed = false
    const processed = false
    const createdBy = response.value["createdBy"]
    const createdAt = this.datepipe.transform((new Date), 'MM/dd/yyyy h:mm:ss');
    const updatedAt = null
    const processStartedAt = null
    const processFinishedAt = null

    var URL = `http://10.0.0.108:8093/addUser?process=${this.selectedProcessType}&inputFile=${inputFile}&outputFile=${outputFile}&processing=${processing}&toBeProcessed=${toBeProcessed}
    &processed=${processed}&createdBy=${createdBy}&createdAt=${createdAt}&updatedAt=${updatedAt}&processStartedAt=${processStartedAt}&processFinishedAt=${processFinishedAt}`;

    this.http.get(URL).subscribe((response) => {
      this.loadData()

    }
    );

  }
}
