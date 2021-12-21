import {Component, Input, OnInit} from '@angular/core';
import {CarResponse} from "../../models/CarResponse";
import {CarService} from "../../service/CarService";
import {RentHistory} from "../../models/RentHistory";

@Component({
  selector: 'app-car-history',
  templateUrl: './car-history.component.html',
  styleUrls: ['./car-history.component.sass']
})
export class CarHistoryComponent implements OnInit {


  @Input()
  rentHistory: RentHistory = new RentHistory("testD")
  car: CarResponse = new CarResponse()
  detailsOpened: boolean = false

  constructor(private carService: CarService) { }

  ngOnInit(): void {
    this.fetchCar()
  }

  changeDetailsState() {
    this.fetchCar()
    this.detailsOpened = !this.detailsOpened
  }

  fetchCar(){
    this.carService.getCar(this.rentHistory.registrationNumber).toPromise()
      .then(response => {
        this.car = response
      })
  }
}
