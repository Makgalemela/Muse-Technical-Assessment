/**
 *
 *  Authour : M  Makgalemela
 *  Date : 12/01/2021
 *
 */
import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { HttpUtilsServiceService } from "../..";
import { environment } from "../../../../environments/environment";


const API_FILE_UPLOAD_URL = environment.baseUrl + "upload";
const API_FIND_SHORTEST_PATH_URL = environment.baseUrl + "path/shortest";

@Injectable({
  providedIn: "root",
})
export class HttpPathService {
  constructor(
    private httpUtils: HttpUtilsServiceService,
    private http: HttpClient
  ) {}

  uploadFile(File: any): Observable<any> {
    const httpHeaders = this.httpUtils.getHTTPHeaders();
    return this.http.post<any>(API_FILE_UPLOAD_URL, File, {
      headers: httpHeaders,
    });
  }

  shortestPath(Path: any): Observable<any> {
    const httpHeaders = this.httpUtils.getHTTPHeaders();
    return this.http.post<any>(API_FIND_SHORTEST_PATH_URL, Path, {
      headers: httpHeaders,
    });
  }
}