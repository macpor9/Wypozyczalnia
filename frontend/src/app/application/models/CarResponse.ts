export class CarResponse {
  model: string
  brand: string
  yearOfProduction: Date
  price: number
  registrationNumber: string
  available: boolean
  availableDate: Date


  constructor(model: string, brand: string, yearOfProduction: Date, price: number, registrationNumber: string, available: boolean, availableDate: Date) {
    this.model = model;
    this.brand = brand;
    this.yearOfProduction = yearOfProduction;
    this.price = price;
    this.registrationNumber = registrationNumber;
    this.available = available;
    this.availableDate = availableDate;
  }


}
