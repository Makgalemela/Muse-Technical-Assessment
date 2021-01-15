
/**
 * 
 *  Authour : M  Makgalemela
 *  Date : 12/01/2021
 * 
 */

import { Component, OnInit } from '@angular/core';
import { HttpPathService } from 'src/app/core';

@Component({
  selector: 'app-upload-file',
  templateUrl: './upload-file.component.html',
  styleUrls: ['./upload-file.component.scss']
})
export class UploadFileComponent implements OnInit {

  constructor(private httpServive : HttpPathService) {}

  fileToUpload: File = null;

  uploaded : boolean = false;

  ngOnInit() {}
  uploadfile(){
    const formData: FormData = new FormData();
    formData.append('file',  this.fileToUpload);
    formData.append('fileName',  this.fileToUpload.name);
    this.httpServive.uploadFile(formData)
    .subscribe((res)=>{
        this.uploaded = !this.uploaded;
        console.log(res)
    })
  }
  handleFileInput(files: FileList) {
    this.fileToUpload = files.item(0);
  }
}
