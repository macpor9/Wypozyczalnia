import {Component, Input, OnInit, Output} from '@angular/core';
import {FilterService} from "../../service/FilterService";
import {CarResponse} from "../../models/CarResponse";
import { EventEmitter } from '@angular/core';
import {SearchCriteria} from "../../models/SearchCriteria";
import {CarService} from "../../service/CarService";
import {SortCriteria} from "../../models/SortCriteria";
import {SortUtil} from "../../../utils/SortUtil";

@Component({
  selector: 'app-filter-window',
  templateUrl: './filter-window.component.html',
  styleUrls: ['./filter-window.component.sass']
})
export class FilterWindowComponent implements OnInit {


  models: string[] = []
  brands: string[] = []
  searchCriteria: SearchCriteria = new SearchCriteria()
  sortOption: SortCriteria = new SortCriteria()
  sortOptions: SortCriteria[] = SortUtil.SORT_OPTIONS

  @Input()
  @Output()
  cars: CarResponse[] = []
  @Output() carsChange = new EventEmitter<CarResponse[]>();


  constructor(private filterService: FilterService, private carService: CarService, ) {
  }

  ngOnInit(): void {
    this.setFilterData()
  }


  setFilterData(){
    this.setModels()
    this.setBrands()
  }


  setModels() {
    console.log("set models")
    console.log(this.searchCriteria.brand)
    this.filterService.getModels(this.searchCriteria.brand).then(
      (e) => {
        this.models = []
        e.forEach(e => this.models.push(e))
        console.log(this.models)
      }
    )
  }

  setBrands() {
    this.filterService.getBrands().then(
      (e) => {
        this.brands = []
        e.forEach(e => this.brands.push(e))
        console.log(this.brands)
      }
    )
  }

  applyFilters() {
    this.carService.getSpecificCars(this.searchCriteria, this.sortOption).then(
      (e => {
        this.cars = []
        e.forEach(val => this.cars.push(Object.assign({}, val)))
        this.carsChange.next(this.cars)
      }),
      err => console.log(err)
    )
  }

  resetFilters() {
    location.reload()
  }
}
