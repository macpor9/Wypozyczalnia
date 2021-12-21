import {Injectable, OnInit} from "@angular/core";
import {HttpClient, HttpParams} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {Constants} from "../../utils/Constants";
import {AuthorizationUtil} from "../../utils/AuthorizationUtil";
import {RentRequest} from "../models/RentRequest";
import {CarResponse} from "../models/CarResponse";
import {RentHistory} from "../models/RentHistory";


@Injectable({providedIn: 'root'})
export class RentService implements OnInit {
  constructor(
    private http: HttpClient,
  ) {
  }

  ngOnInit() {
  }

  rentCar(registrationNumber: string, rentRequest: RentRequest){
    this.http.patch(environment.apiUrl + Constants.RENT_CAR_REQUEST_URL + registrationNumber, rentRequest,{headers: AuthorizationUtil.getJsonHeaders()}).toPromise()
      .then(e => location.reload())
  }

  getRentHistory() {
    return this.http.get<RentHistory[]>(environment.apiUrl + Constants.GET_USER_RENT_HISTORY,
      {headers: AuthorizationUtil.getJsonHeaders()})
  }
}

