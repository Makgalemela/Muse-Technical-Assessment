
/**
 * 
 *  Authour : M  Makgalemela
 *  Date : 12/01/2021
 * 
 */

// Angular
import { Component, OnInit, ViewChild } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { CompRefreshService } from '../../../../core/index';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  @ViewChild(RouterOutlet ,{static:false}) outlet: RouterOutlet;

  constructor(public toggle : CompRefreshService) { }

   appName : String = "Shortest Path From Point to Point"
  ngOnInit() {
  }
  

}
