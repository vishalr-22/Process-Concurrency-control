// import * as Stomp from 'stompjs';
// import * as SockJS from 'sockjs-client';
// import { TableComponent } from './app/table/table.component';
// // please use the correct path
// // import * as StompJs from '../node_modules/@stomp/stompjs/esm5/index';
// // import * as Stomp from '../node_modules/@stomp/stompjs/esm6/index'
// // import { AppComponent } from './app.component';

// export class websocket {
//     webSocketEndPoint: string = 'http://localhost:8080/ws';
//     topic: string = "/topic/greetings";
//     stompClient: any;
//     tableComponent: TableComponent;
//     constructor(tableComponent: TableComponent){
//         this.tableComponent = tableComponent;
//     }
//     _connect() {
//         console.log("Initialize WebSocket Connection");
//         let ws = new SockJS(this.webSocketEndPoint);
//         this.stompClient = Stomp.over(ws);
//         const _this = this;
//         _this.stompClient.connect({}, function (frame:any) {
//             _this.stompClient.subscribe(_this.topic, function (sdkEvent:any) {
//                 _this.onMessageReceived(sdkEvent);
//             });
//             //_this.stompClient.reconnect_delay = 2000;
//         }, this.errorCallBack);
//     };

//     _disconnect() {
//         if (this.stompClient !== null) {
//             this.stompClient.disconnect();
//         }
//         console.log("Disconnected");
//     }

//     // on error, schedule a reconnection attempt
//     errorCallBack(error:any) {
//         console.log("errorCallBack -> " + error)
//         setTimeout(() => {
//             this._connect();
//         }, 5000);
//     }

//  /**
//   * Send message to sever via web socket
//   * @param {*} message 
//   */
//     _send(message:any) {
//         console.log("calling logout api via web socket");
//         this.stompClient.send("/app/hello", {}, JSON.stringify(message));
//     }

//     onMessageReceived(message:any) {
//         console.log("Message Recieved from Server :: " + message);
//         this.tableComponent.handleMessage(JSON.stringify(message.body));
//     }
// }