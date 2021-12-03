import {Injectable, OnInit} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {Constants} from "../../utils/Constants";
import {Car} from "../models/Car";
import {AuthorizationUtil} from "../../utils/AuthorizationUtil";
import {CarResponse} from "../models/CarResponse";


@Injectable({providedIn: 'root'})
export class CarService implements OnInit {
  constructor(
    private http: HttpClient,
  ) {
  }

  ngOnInit() {
  }

  addCar(car: Car) {
    return this.http.post(
      environment.apiUrl + Constants.ADD_CAR_REQUEST_URL,
      car,
      {headers: AuthorizationUtil.jsonHeaders})
      .toPromise().then(e => console.log(e))
  }

  uploadCarPhoto(formData: FormData, registrationNumber: string){
    return this.http.post(environment.apiUrl + Constants.ADD_CAR_PHOTO_REQUEST_URL + registrationNumber,
      formData,
      {headers: AuthorizationUtil.multipartHeaders})
  }

  getAllCars(){
    return this.http.get<CarResponse[]>(environment.apiUrl + Constants.GET_ALL_CARS_REQUEST_URL,
      {headers: AuthorizationUtil.jsonHeaders})
  }
}

