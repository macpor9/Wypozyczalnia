import {Component, Input, OnInit, Output} from '@angular/core';
import {FilterService} from "../../service/FilterService";
import {CarResponse} from "../../models/CarResponse";
import { EventEmitter } from '@angular/core';
import {SearchCriteria} from "../../models/SearchCriteria";
import {CarService} from "../../service/CarService";

@Component({
  selector: 'app-filter-window',
  templateUrl: './filter-window.component.html',
  styleUrls: ['./filter-window.component.sass']
})
export class FilterWindowComponent implements OnInit {


  models: string[] = []
  brands: string[] = []
  searchCriteria: SearchCriteria = SearchCriteria.createEmptySearchCriteria()
  sortFields: string = "price"
  sortMode: string = "descending"


  @Input()
  @Output()
  cars: CarResponse[] = []
  @Output() carsChange = new EventEmitter<CarResponse[]>();


  constructor(private filterService: FilterService, private carService: CarService) {
  }

  ngOnInit(): void {
    this.setFilterData()
  }


  setFilterData(){
    this.setModels()
    this.setBrands()
  }


  setModels() {
    this.filterService.getModels().then(
      (e) => {
        e.forEach(e => this.models.push(e))
        console.log(this.models)
      }
    )
  }

  setBrands() {
    this.filterService.getBrands().then(
      (e) => {
        e.forEach(e => this.brands.push(e))
        console.log(this.brands)
      }
    )
  }

  applyFilters() {
    console.log("apply")
    this.carService.getSpecificCars(this.searchCriteria, this.sortFields, this.sortMode).then(
      (e => {
        this.cars = []
        e.forEach(val => this.cars.push(Object.assign({}, val)))
        console.log("number of cars: " + this.cars.length)
        this.carsChange.next(this.cars)
      }),
      err => console.log(err)
    )
  }
}
