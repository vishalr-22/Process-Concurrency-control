import { DatePipe } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { InteractionService } from '../interaction.service';

@Component({
  selector: 'app-processtable',
  templateUrl: './processtable.component.html',
  styleUrls: ['./processtable.component.css']
})
export class ProcesstableComponent implements OnInit {
  id: any;

  constructor(private http: HttpClient, public datepipe: DatePipe,private _interactionService: InteractionService) { }

  temp:number = 0

  selectedRowIndex = 0
  // mat progress bar
  isLoading = false;
  // loading table data
  Element_data = [];
  dataSource = new MatTableDataSource(this.Element_data);
  // columns that are displayed
  displayedColumns: string[] = ["id", "processId", "processTime", "progress"];

  // paginator size options
  pageSizeOptions: number[] = [5, 10, 25, 100];

  // paginator page size option
  pageSize = 10;

  // paginator current page
  currentPage = 0;

  // progress %
  progress = 0;
  incr = 1
  progressFlag: any

  ngOnInit(): void {
    // setInterval(() => this.manageProgress(), 150 )
    // this.id = setInterval(() => {
    //   this.manageProgress(); 
    // }, 100);

    this.loadData()
    
    this._interactionService.scrollMsg$
      .subscribe(
        message => {
          this.loadData()
          console.log(message)
          if (message != "Reload now"){
          var numb: any
          try{
            numb = message.match(/\d/g);
            numb = numb.join("");
          }
          catch{
            
          }
          this.progressFlag = true
          this.progress = numb
          console.log(this.progress)
          this.manageProgress(numb)

        }
      }
      )
  }

  ngOnDestroy() {
    if (this.id) {
      clearInterval(this.id);
    }
  }

  // paginator page changed
  pageChanged(event: PageEvent) {
    console.log({ event });
    this.pageSize = event.pageSize;
    this.currentPage = event.pageIndex;
    // this.loadData() 
  }

  
  @ViewChild('paginator') paginator!: MatPaginator;
  @ViewChild('empTbSort') empTbSort = new MatSort();


  // loading of data
  loadData() {
    this.isLoading = true;
    var URL = `http://10.0.0.108:8093/fetchProcesses`;

    this.http.get(URL).subscribe((response) => {
      this.Element_data = JSON.parse(JSON.stringify(response))

      if(JSON.parse(JSON.stringify(response)).length == 1){
        // this.progressFlag = true
      }
      console.log(this.Element_data);
      this.dataSource = new MatTableDataSource(this.Element_data);
      this.isLoading = false;

    }
    );
  }

  sleep(ms:any) {
    return new Promise(resolve => setTimeout(resolve, ms));
  }
  
  
  manageProgress(i:any) {
    if(i<=100){
    // this.progress = this.progress + this.incr;
      this.progress = i
      console.log(this.progress)
      // await this.sleep(4000);
    }
    else{
      this.progress = 0
    }

  }


}
