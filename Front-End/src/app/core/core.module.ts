/**
 * 
 *  Authour : M  Makgalemela
 *  Date : 12/01/2021
 * 
 */
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { InterceptService } from './utils/interceptor.service';
import { AdministrationComponentService } from './utils/administration-component.service';

@NgModule({
  declarations: [],
  imports: [
    CommonModule
  ],
  providers :[InterceptService],
  entryComponents :[]
})
export class CoreModule { }
