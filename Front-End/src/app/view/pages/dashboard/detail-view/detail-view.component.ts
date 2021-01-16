
/**
 * 
 *  Authour : M  Makgalemela
 *  Date : 12/01/2021
 * 
 */

// Angular
import { Component, OnInit } from "@angular/core";
import { FormControl, FormGroup, FormsModule } from "@angular/forms";
import { MatTableDataSource } from "@angular/material/table";
import { BehaviorSubject, combineLatest, Observable } from "rxjs";
import { of } from "rxjs/internal/observable/of";
import { map, startWith } from "rxjs/operators";
import { HttpPathService, SearchFormService } from "src/app/core";

export interface ResultSet {
  Origin: string;
  Destination: String;
  Distance: String;
}

@Component({
  selector: "app-detail-view",
  templateUrl: "./detail-view.component.html",
  styleUrls: ["./detail-view.component.scss"],
})
export class DetailViewComponent implements OnInit {
  options: string[] = [];
  checked = false;


  origin: String = "Johannesburg";
  destination: String = "Cape Town";
  distance: String = "1234495432212134 km";
  constructor(
    private Forms: SearchFormService,
    private httpServive: HttpPathService
  ) {}

  form: FormGroup = this.Forms.emailForm;

  searchFilter$: Observable<any>;

  results$: BehaviorSubject<any> = new BehaviorSubject<any>(false);
  errorResults$ : BehaviorSubject<any> = new BehaviorSubject<any>("Results Loading..")
  displayedColumns: string[] = ["Origin", "Destination", "Distance"];

  ngOnInit() {
    this.httpServive.getEndPoints({}).subscribe((sites) => {
      let data: any[] = sites.data;
      data.forEach((elem) => {
        this.options.push(elem.planetName);
        this.searchFilter$ = of(this.options)
      });
    });


    this.form.get("destination").valueChanges.subscribe(word=>{
       this.searchFilter$  =  of(this._filter(word))
    })

    this.form.get("origin").valueChanges.subscribe(word=>{
      this.searchFilter$  = of(this._filter(word));
    });
  }

  private _filter(value: string): string[] {
    const filterValue = value.toLowerCase();
    return this.options.filter((option) =>
      option.toLowerCase().includes(filterValue)
    );
  }

  dataSource = new MatTableDataSource<ResultSet>();

  findShortestPath() {
    this.results$.next(false);
    console.log(this.form.value)
    this.httpServive.shortestPath(this.form.value).subscribe((res) => {
      if(res.isSuccess){
        this.results$.next(res.data);
        let tableData: ResultSet[] = [];
        let row: ResultSet = {
          Destination: res.data.destination,
          Distance: res.data.distance,
          Origin: res.data.origin,
        };
        tableData.push(row);
        this.dataSource = new MatTableDataSource<ResultSet>(tableData);
      }else{
        this.errorResults$.next(res.message)
      }
    });
  }
}
