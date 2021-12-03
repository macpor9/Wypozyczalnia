import {Component, OnInit} from '@angular/core';
import {CarService} from "../../service/CarService";
import {CarResponse} from "../../models/CarResponse";
import {map} from "rxjs/operators";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.sass', '../styles.sass']
})
export class HomeComponent implements OnInit {

  cars: CarResponse[] = []

  constructor(private carService: CarService) {
  }

  ngOnInit(): void {
    this.getCarList()
  }

  getCarList(){
    this.carService.getAllCars().toPromise().then(
      (e) => e.forEach(val => this.cars.push(Object.assign({}, val))))
  }
  logCars(){
    console.log("cars: " + this.cars[0].price)
  }
}

