import {Injectable, OnInit} from "@angular/core";
import {HttpClient, HttpParams} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {Constants} from "../../utils/Constants";
import {AuthorizationUtil} from "../../utils/AuthorizationUtil";
import {SortCriteria} from "../models/SortCriteria";

@Injectable({providedIn: 'root'})
export class FilterService implements OnInit {

  constructor(
    private http: HttpClient,
  ) {
  }

  ngOnInit() {
  }

  getModels(brand: string | undefined) {
    let params = new HttpParams();
    if(brand !== undefined){
      params = params.append("brand", brand)
    }
    return this.http.get<string[]>(environment.apiUrl + Constants.GET_MODELS_REQUEST_URL,
      {headers: AuthorizationUtil.getJsonHeaders(), params: params}).toPromise()
  }

  getBrands() {
    return this.http.get<string[]>(environment.apiUrl + Constants.GET_BRANDS_REQUEST_URL,
      {headers: AuthorizationUtil.getJsonHeaders()}).toPromise()
  }


}

