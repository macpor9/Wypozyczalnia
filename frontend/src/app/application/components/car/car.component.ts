import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {CarResponse} from "../../models/CarResponse";
import {CarService} from "../../service/CarService";
import {Constants} from "../../../utils/Constants";
import {environment} from "../../../../environments/environment";
import {UserService} from "../../service/UserService";
import {DatePipe} from "@angular/common";

@Component({
  selector: 'app-car',
  templateUrl: './car.component.html',
  styleUrls: ['./car.component.sass']
})
export class CarComponent implements OnInit {


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

  rentCar() {
    this.carService.rentCar(this.car.registrationNumber, new Date(), new Date())
  }
}
