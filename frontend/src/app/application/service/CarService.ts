import {Injectable, OnInit} from "@angular/core";
import {HttpClient, HttpParams} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {Constants} from "../../utils/Constants";
import {Car} from "../models/Car";
import {AuthorizationUtil} from "../../utils/AuthorizationUtil";
import {CarResponse} from "../models/CarResponse";
import {SearchCriteria} from "../models/SearchCriteria";
import {RentRequest} from "../models/RentRequest";


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
      {headers: AuthorizationUtil.getJsonHeaders()})
      .toPromise().then(e => console.log(e))
  }

  updateCar(car: Car, initialRegistrationNo: string) {
    return this.http.put(
      environment.apiUrl + Constants.REMOVE_CAR_REQUEST_URL + initialRegistrationNo,
      car,
      {headers: AuthorizationUtil.getJsonHeaders()})
      .toPromise().then(e => console.log(e))
  }

  uploadCarPhoto(formData: FormData, registrationNumber: string) {
    return this.http.post(environment.apiUrl + Constants.ADD_CAR_PHOTO_REQUEST_URL + registrationNumber,
      formData,
      {headers: AuthorizationUtil.getMultipartHeaders()})
  }

  getAllCars() {
    return this.http.get<CarResponse[]>(environment.apiUrl + Constants.GET_ALL_CARS_REQUEST_URL,
      {headers: AuthorizationUtil.getJsonHeaders()})
  }

  removeCar(registrationNumber: string){
    return this.http.delete(environment.apiUrl + Constants.REMOVE_CAR_REQUEST_URL + registrationNumber,
      {headers: AuthorizationUtil.getJsonHeaders()})

  }

  getCar(registrationNumber: string){
    return this.http.get<CarResponse>(environment.apiUrl + Constants.GET_CAR_REQUEST_URL + registrationNumber,
      {headers: AuthorizationUtil.getJsonHeaders()})
  }

  getPhotoUrl(registrationNumber: string) {
    return this.http.get<string>(environment.apiUrl + Constants.ADD_CAR_PHOTO_REQUEST_URL + registrationNumber,
      {headers: AuthorizationUtil.getJsonHeaders()})
  }

  getSpecificCars(searchCriteria: SearchCriteria, sortField: string, sortMode: string){
    let params = new HttpParams();
    params = params.append("field", sortField)
    params = params.append("mode",sortMode)
    return this.http.post<CarResponse[]>(environment.apiUrl + Constants.GET_SPECIFIC_CARS_REQUEST_URL, searchCriteria,
      {headers: AuthorizationUtil.getJsonHeaders(), params: params}).toPromise()
  }

  rentCar(registrationNumber: string, rentRequest: RentRequest){
    this.http.patch(environment.apiUrl + Constants.RENT_CAR_REQUEST_URL + registrationNumber, rentRequest,{headers: AuthorizationUtil.getJsonHeaders()}).toPromise()
      .then(e => location.reload())
  }
}

