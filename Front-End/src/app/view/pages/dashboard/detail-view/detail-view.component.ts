import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormsModule } from '@angular/forms';
import { BehaviorSubject, combineLatest, Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { HttpPathService, SearchFormService } from 'src/app/core';

@Component({
  selector: 'app-detail-view',
  templateUrl: './detail-view.component.html',
  styleUrls: ['./detail-view.component.scss']
})
export class DetailViewComponent implements OnInit {

  options: string[] = ['One', 'Two', 'Three'];
  filteredOptions: Observable<string[]>;

  origin : String ="Johannesburg"
  destination : String ="Cape Town"
  distance : String ="1234495432212134 km"
  constructor(private Forms : SearchFormService ,private httpServive : HttpPathService){}

  form : FormGroup = this.Forms.emailForm;
  originFilter$: Observable<any>;
  destinationFilter$: Observable<any>; 
  results$ : BehaviorSubject<any> = new BehaviorSubject<any>(false);

  ngOnInit() {

  this.httpServive.getEndPoints({}).subscribe(res=>{
        this.originFilter$ =  combineLatest(this.httpServive.getEndPoints({}).pipe(startWith('')),
        this.filteredOptions = this.form.get('origin').valueChanges
        .pipe(
          map(([sites ,filterString])=> sites.filter(site=>site.siteName.toLowerCase()
          .indexOf(filterString.toLowerCase()) !== -1))))
    })
 

    this.httpServive.getEndPoints({}).subscribe(res=>{
      this.destinationFilter$ =  combineLatest(this.httpServive.getEndPoints({}).pipe(startWith('')),
      this.filteredOptions = this.form.get('destination').valueChanges
      .pipe(
        map(([sites ,filterString])=> sites.filter(site=>site.siteName.toLowerCase()
        .indexOf(filterString.toLowerCase()) !== -1))))
  })
  }

  private _filter(value: string): string[] {
    const filterValue = value.toLowerCase();
    return this.options.filter(option => option.toLowerCase().includes(filterValue));

  }


  findShortestPath(){
     this.httpServive.shortestPath(this.form.value).subscribe(res=>{
       this.results$.next(res)
     })
    
  }
}
