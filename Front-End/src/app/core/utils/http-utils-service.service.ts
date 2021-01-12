
/**
 * 
 *  Authour : M  Makgalemela
 *  Date : 12/01/2021
 * 
 */
import { HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment'


@Injectable({
  providedIn: 'root'
})
export class HttpUtilsServiceService {

  constructor() { }


  getHTTPHeaders(): HttpHeaders {
		let token = localStorage.getItem(environment.authTokenKey);
		const  httpHeaders = new HttpHeaders({
			'Content-Type' : 'application/json; charset=utf-8',
			'Accept'       : 'application/json',
			'Access-Control-Allow-Origin': '*',
			'Authorization': `Bearer ${token}`,
		});
		return  httpHeaders;
	}
}
