import {Component, OnInit} from '@angular/core';
import {CarService} from "../../service/CarService";
import {CarResponse} from "../../models/CarResponse";
import {map} from "rxjs/operators";
import {isWildcardReexportStatement} from "@angular/compiler-cli/ngcc/src/host/commonjs_umd_utils";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.sass']
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
      (e) => {
        console.log("av: " + e[0].available)
        e.forEach(val => this.cars.push(Object.assign({}, val)))
      })
  }
  logCars(){
    console.log("cars: " + this.cars[0].price)
  }

  deleteCar(car: CarResponse){
    this.cars = this.cars.filter(function(el) { return el.registrationNumber != car.registrationNumber; });
  }

  listCars() {
    console.log("home cars number:" + this.cars.length)
  }

  filterCars(cars: CarResponse[]){
    this.cars = cars
  }
}

