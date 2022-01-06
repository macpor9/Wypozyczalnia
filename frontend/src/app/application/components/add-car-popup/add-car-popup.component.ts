import { EventEmitter } from '@angular/core';
import {Component, Input, OnInit, Output} from '@angular/core';
import {Car} from "../../models/Car";
import {CarService} from "../../service/CarService";
import {File} from "@angular/compiler-cli/src/ngtsc/file_system/testing/src/mock_file_system";

@Component({
  selector: 'app-add-car-popup',
  templateUrl: './add-car-popup.component.html',
  styleUrls: ['./add-car-popup.component.sass']
})
export class AddCarPopupComponent implements OnInit {
  STATUS_INITIAL = 0;
  STATUS_SAVING = 1;
  STATUS_SUCCESS = 2;
  STATUS_FAILED = 3;



  @Input()
  updating = false
  @Input() popup = true
  @Output() popupChange = new EventEmitter<boolean>();
  @Input()
  car: Car = new Car("","",new Date(), "",0)
  currentStatus: number = this.STATUS_INITIAL
  formData: FormData = new FormData()
  filename: string = ''

  initialRegistrationNo: string = "0"

  constructor(private carService: CarService) {
  }

  ngOnInit(): void {
    this.initialRegistrationNo = this.car.registrationNumber
  }

  closePopup(){
    this.popupChange.emit(false)
  }
  filesChange(event: any) {
    this.currentStatus = this.STATUS_SAVING;
    let file = event.target.files[event.target.files.length-1]
    this.filename = file.name

    if(file) {
      this.formData.delete("file")
      this.formData.append("file", file, file.name)
    }
  }

  save(formData: FormData) {
    if(formData.get("file") !== null){
      this.carService.uploadCarPhoto(formData, this.car.registrationNumber)
        .toPromise()
        .then(x => {
          console.log("photo uploaded with success")
          location.reload()
        })
        .catch(err => {
          console.log("photo not uploaded error")
        });
    }
  }

  addCar() {
    this.carService.addCar(this.car)
      .then(e => {
        this.save(this.formData)
        this.currentStatus = this.STATUS_SUCCESS
      })
      .catch(err => {
        this.currentStatus = this.STATUS_FAILED;
      });
    console.log(this.currentStatus)
  }

  updateCar() {
    this.carService.updateCar(this.car, this.initialRegistrationNo)
      .then(x => {
        this.save(this.formData)
        this.currentStatus = this.STATUS_SUCCESS;
        location.reload()
      })
      .catch(err => {
        this.currentStatus = this.STATUS_FAILED;
      });

    console.log("st: " + this.currentStatus)
    console.log(this.updating)
  }

  isInitial() {
    return this.currentStatus === this.STATUS_INITIAL;
  }
  isSaving() {
    return this.currentStatus === this.STATUS_SAVING;
  }
  isSuccess() {
    return this.currentStatus === this.STATUS_SUCCESS;
  }
  isFailed() {
    return this.currentStatus === this.STATUS_FAILED;
  }
}
