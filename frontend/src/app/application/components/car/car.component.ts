import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {CarResponse} from "../../models/CarResponse";
import {CarService} from "../../service/CarService";
import {Constants} from "../../../utils/Constants";
import {environment} from "../../../../environments/environment";
import {UserService} from "../../service/UserService";
import {DatePipe} from "@angular/common";
import {RentRequest} from "../../models/RentRequest";

@Component({
  selector: 'app-car',
  templateUrl: './car.component.html',
  styleUrls: ['./car.component.sass']
})
export class CarComponent implements OnInit {

  rentPopup: boolean = false
  rentRequest: RentRequest = RentRequest.createEmptyRentRequest()

  currentPrice: number = 0
  currentRentDays = 0

  @Input()
  @Output()
  opened: boolean = false

  @Input()
  car: CarResponse = new CarResponse("","",new Date(),0,"",true, new Date())

  @Output()
  carChange = new EventEmitter<CarResponse>();

  picture: string = ""
  constructor(private carService: CarService, public userService: UserService) {
  }

  ngOnInit(): void {
    this.picture = environment.apiUrl + Constants.ADD_CAR_PHOTO_REQUEST_URL + this.car.registrationNumber
  }

  removeCar(){
    console.log("removing: " + this.car.model)
    this.carService.removeCar(this.car.registrationNumber).toPromise()
      .then(e => this.carChange.next(this.car))
  }

  openPopup() {
    this.opened = true
  }

  showAvailability(): string {
    if(this.car.available)
      return "available now!"
    return "available at: " + new Date(this.car.availableDate).toISOString().slice(0,10);  }

  changeRentPopupState(){
    this.rentPopup = !this.rentPopup
  }

  rentCar() {
    this.carService.rentCar(this.car.registrationNumber, this.rentRequest)
  }

  refreshCurrentPrice(){
    let from = new Date(this.rentRequest.reservedFrom)
    let until = new Date(this.rentRequest.reservedUntil)
    let time = until.getTime() - from.getTime()
    this.currentRentDays = Math.ceil((time / (1000 * 3600 * 24) + 1))
    this.currentPrice = this.car.price * this.currentRentDays
    console.log(this.currentRentDays)
  }
}
