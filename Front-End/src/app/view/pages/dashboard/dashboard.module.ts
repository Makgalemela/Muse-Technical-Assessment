import { Component, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DashboardComponent } from './dashboard/dashboard.component';
import { UploadFileComponent } from './upload-file/upload-file.component';
import { DetailViewComponent } from './detail-view/detail-view.component';
import { RouterModule, Routes } from '@angular/router';
import { MatDividerModule } from '@angular/material/divider';

const routes: Routes = [
  {
    path : '',
    component : DashboardComponent ,
    children :[
      {
        path : 'search',
        component : DetailViewComponent
      },
      {
        path : 'upload',
        component : UploadFileComponent
      }
    ]
  }
  
]


@NgModule({
  declarations: [DashboardComponent, UploadFileComponent, DetailViewComponent],
  imports: [
    CommonModule,
    CommonModule,
    RouterModule.forChild(routes),
    MatDividerModule
  ]
})
export class DashboardModule { }
