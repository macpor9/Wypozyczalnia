import {Component, Input, OnInit} from '@angular/core';
import {CarResponse} from "../../models/CarResponse";
import {CarService} from "../../service/CarService";
import {Constants} from "../../../utils/Constants";
import {environment} from "../../../../environments/environment";

@Component({
  selector: 'app-car',
  templateUrl: './car.component.html',
  styleUrls: ['./car.component.sass']
})
export class CarComponent implements OnInit {

  @Input()
  car: CarResponse = new CarResponse("","",new Date(),0,"",true, new Date())
  picture: string = ""
  constructor(private carService: CarService) {
  }

  ngOnInit(): void {
    this.picture = environment.apiUrl + Constants.ADD_CAR_PHOTO_REQUEST_URL + this.car.registrationNumber
  }

}
