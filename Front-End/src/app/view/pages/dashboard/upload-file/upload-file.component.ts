
/**
 * 
 *  Authour : M  Makgalemela
 *  Date : 12/01/2021
 * 
 */

import { Component, OnInit } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { CompRefreshService, HttpPathService } from '../../../../core/index';

@Component({
  selector: 'app-upload-file',
  templateUrl: './upload-file.component.html',
  styleUrls: ['./upload-file.component.scss']
})
export class UploadFileComponent implements OnInit {

  constructor(private httpServive : HttpPathService ,  public toggle : CompRefreshService) {}

  fileToUpload: File = null;

  uploaded : boolean = false;

  response$ : BehaviorSubject<String> = new BehaviorSubject<String>("")

  ngOnInit() {}
  uploadfile(){
    if(this.uploaded){
      this.uploaded = false;
    }
    const formData: FormData = new FormData();
    formData.append('file',  this.fileToUpload);
    formData.append('fileName',  this.fileToUpload.name);
    this.httpServive.uploadFile(formData)
    .subscribe((res)=>{
        this.toggle.hideUpload();
        this.response$.next(res.message)
    })
  }
  handleFileInput(files: FileList) {
    this.fileToUpload = files.item(0);
  }
}
