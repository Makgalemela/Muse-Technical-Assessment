import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DashboardModule } from '../app/view/pages/dashboard/dashboard.module';
import { BaseComponent } from './view/pages/base/base.component';
import { DashboardComponent } from './view/pages/dashboard/dashboard/dashboard.component';


const routes: Routes = [
  {
     path : '',
     component :BaseComponent,
     children :[
      {
        path :'home',
        loadChildren: () => import('src/app/view/pages/dashboard/dashboard.module').then(m => m. DashboardModule), 
      },
      { path: '', redirectTo: '/home', pathMatch: 'full' },

      // This temporary redirect when path when the url does not exists.
      //  { path: '**', redirectTo: '/home/search', pathMatch: 'full' },
     ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }


