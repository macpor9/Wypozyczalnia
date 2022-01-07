import { Component, OnInit } from '@angular/core';
import {RentHistory} from "../../models/RentHistory";
import {RentService} from "../../service/RentService";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.sass']
})
export class ProfileComponent implements OnInit {

  rents: RentHistory[] = []

  constructor(private rentService: RentService) { }

  ngOnInit(): void {
    this.getRentHistory()
  }

  getRentHistory(){
    this.rentService.getRentHistory().toPromise().then(
      (e) => {
        e.forEach(val => this.rents.push(Object.assign({}, val)))
      })
  }

}
