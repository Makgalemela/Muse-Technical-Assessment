
/**
 * 
 *  Authour : M  Makgalemela
 *  Date : 12/01/2021
 * 
 */

// Angular
import { Injectable } from '@angular/core';
import { HttpEvent, HttpInterceptor, HttpHandler, HttpRequest, HttpResponse, HttpErrorResponse } from '@angular/common/http';
// RxJS
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';

@Injectable({
	providedIn: "root",
  })
export class InterceptService implements HttpInterceptor {
	constructor() {}
	intercept(
		request: HttpRequest<any>,
		next: HttpHandler
	): Observable<HttpEvent<any>> {
			return next.handle(request)
				.pipe(
					tap(
						event => {
							if (event instanceof HttpResponse) {}
						},
						() => {	}
					)
				);
		}
}
