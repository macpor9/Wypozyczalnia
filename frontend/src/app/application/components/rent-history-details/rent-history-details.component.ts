import {Component, Input, OnInit} from '@angular/core';
import {CarResponse} from "../../models/CarResponse";
import {environment} from "../../../../environments/environment";
import {Constants} from "../../../utils/Constants";
import {RentHistory} from "../../models/RentHistory";

@Component({
  selector: 'app-rent-history-details',
  templateUrl: './rent-history-details.component.html',
  styleUrls: ['./rent-history-details.component.sass']
})
export class RentHistoryDetailsComponent implements OnInit {

  constructor() { }

  @Input()
  rentHistory: RentHistory = new RentHistory()

  @Input()
  car: CarResponse = new CarResponse()

  picture: string = ""

  ngOnInit(): void {
    this.picture = environment.apiUrl + Constants.ADD_CAR_PHOTO_REQUEST_URL + this.car.registrationNumber
  }

  getRentDays(): number {
    let from = new Date(this.rentHistory.reservedFrom)
    let until = new Date(this.rentHistory.reservedUntil)
    let time = until.getTime() - from.getTime()
    return Math.ceil((time / (1000 * 3600 * 24) + 1))
  }
  getFromString() {
  return new Date(this.rentHistory.reservedFrom).toISOString().slice(0,10);
  }
  getUntilString() {
  return new Date(this.rentHistory.reservedUntil).toISOString().slice(0,10);
  }

}
