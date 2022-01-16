import {Component, OnInit} from '@angular/core';
import {CarService} from "../../service/CarService";
import {CarResponse} from "../../models/CarResponse";

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
        e.forEach(val => this.cars.push(Object.assign({}, val)))
      })
  }

  deleteCar(car: CarResponse){
    location.reload()
    // this.cars = this.cars.filter(function(el) { return el.registrationNumber != car.registrationNumber; });
  }

  listCars() {
    console.log("home cars number:" + this.cars.length)
  }

  filterCars(cars: CarResponse[]){
    this.cars = cars
  }
}

