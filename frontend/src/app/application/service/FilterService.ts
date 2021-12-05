import {Injectable, OnInit} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {Constants} from "../../utils/Constants";
import {AuthorizationUtil} from "../../utils/AuthorizationUtil";

@Injectable({providedIn: 'root'})
export class FilterService implements OnInit {

  constructor(
    private http: HttpClient,
  ) {
  }

  ngOnInit() {
  }

 getModels(){
    return this.http.get<string[]>(environment.apiUrl + Constants.GET_MODELS_REQUEST_URL,
      {headers: AuthorizationUtil.getJsonHeaders()}).toPromise()
 }

 getBrands(){
    return this.http.get<string[]>(environment.apiUrl + Constants.GET_BRANDS_REQUEST_URL,
      {headers: AuthorizationUtil.getJsonHeaders()}).toPromise()
 }

}

