import { Component, OnInit, ViewChild } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { AdministrationComponentService } from 'src/app/core';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  @ViewChild(RouterOutlet ,{static:false}) outlet: RouterOutlet;

  constructor() {
   }

  ngOnInit() {
  }

}
