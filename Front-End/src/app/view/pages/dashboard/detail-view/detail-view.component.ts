import { Component, OnInit } from "@angular/core";
import { FormControl, FormGroup, FormsModule } from "@angular/forms";
import { MatTableDataSource } from "@angular/material/table";
import { BehaviorSubject, combineLatest, Observable } from "rxjs";
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

  origin: String = "Johannesburg";
  destination: String = "Cape Town";
  distance: String = "1234495432212134 km";
  constructor(
    private Forms: SearchFormService,
    private httpServive: HttpPathService
  ) {}

  form: FormGroup = this.Forms.emailForm;
  originFilter$: Observable<any>;
  destinationFilter$: Observable<any>;
  results$: BehaviorSubject<any> = new BehaviorSubject<any>(false);
  displayedColumns: string[] = ["Origin", "Destination", "Distance"];

  ngOnInit() {
    this.httpServive.getEndPoints({}).subscribe((sites) => {
      let data: any[] = sites.data;
      data.forEach((elem) => {
        this.options.push(elem.planetName);
      });
    });

    this.originFilter$ = this.form.get("origin").valueChanges.pipe(
      startWith(" "),
      map((value) => this._filter(value))
    );

    this.destinationFilter$ = this.form.get("destination").valueChanges.pipe(
      startWith(" "),
      map((value) => this._filter(value))
    );
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
    this.httpServive.shortestPath(this.form.value).subscribe((res) => {
      this.results$.next(res.data);
      let tableData: ResultSet[] = [];
      let row: ResultSet = {
        Destination: res.data.destination,
        Distance: res.data.distance,
        Origin: res.data.origin,
      };
      tableData.push(row);
      this.dataSource = new MatTableDataSource<ResultSet>(tableData);
    });
  }
}
