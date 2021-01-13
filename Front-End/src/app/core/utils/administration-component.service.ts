import { Injectable, ViewChild } from '@angular/core';
import { RouterOutlet, Router, ActivationStart } from '@angular/router';


@Injectable({
  providedIn: 'root'
})
export class AdministrationComponentService {


  constructor( private router: any) { }
  deativateActivateOutlet(outlet: RouterOutlet) : void{
    this.router.events.subscribe(e => {
      if (e instanceof ActivationStart && e.snapshot.outlet === "administration")
        outlet.deactivate();
    });
  }
}
