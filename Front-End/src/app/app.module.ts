import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CoreModule, HttpUtilsServiceService } from './core';
import { DashboardModule } from './view/pages/dashboard';
import { BaseModule } from './view/pages/base/base.module';
import { AutocompleteLibModule } from 'angular-ng-autocomplete';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule ,
    CoreModule, 
    BaseModule,
    DashboardModule,
    AutocompleteLibModule,
    FontAwesomeModule
  ],
  providers: [HttpUtilsServiceService],
  bootstrap: [AppComponent]
})
export class AppModule { }
