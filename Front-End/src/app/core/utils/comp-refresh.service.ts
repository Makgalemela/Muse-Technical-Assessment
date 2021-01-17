import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CompRefreshService {
  
  toggleUpload$ : BehaviorSubject<boolean> = new BehaviorSubject<boolean>(true);
  showUpload(){
      this.toggleUpload$.next(true);
  }
  hideUpload(){
    this.toggleUpload$.next(false);
  }
   
  getToggleStatus(){
    return this.toggleUpload$.getValue();
  }

  constructor() { }
}
