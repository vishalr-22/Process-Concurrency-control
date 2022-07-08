import { Injectable } from '@angular/core';
import { Subject } from 'rxjs/internal/Subject';

@Injectable({
  providedIn: 'root'
})
export class InteractionService {

  // table to process load
  private _msgSource = new Subject<string>();
  scrollMsg$ = this._msgSource.asObservable();

  // table to process load
  sendMessageTableprocess(message: any){
    this._msgSource.next(message)
  }


  constructor() { }
}
