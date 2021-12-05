import {Component, OnInit} from '@angular/core';
import {FilterService} from "../../service/FilterService";

@Component({
  selector: 'app-filter-window',
  templateUrl: './filter-window.component.html',
  styleUrls: ['./filter-window.component.sass']
})
export class FilterWindowComponent implements OnInit {


  models: string[] = []
  brands: string[] = []

  constructor(private carService: FilterService) {
  }

  ngOnInit(): void {
    this.setFilterData()
  }


  setFilterData(){
    this.setModels()
    this.setBrands()
  }


  setModels() {
    this.carService.getModels().then(
      (e) => {
        e.forEach(e => this.models.push(e))
        console.log(this.models)
      }
    )
  }

  setBrands() {
    this.carService.getBrands().then(
      (e) => {
        e.forEach(e => this.brands.push(e))
        console.log(this.brands)
      }
    )
  }

}
