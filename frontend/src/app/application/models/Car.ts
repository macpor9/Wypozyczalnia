export class Car{
  brand: string
  model: string
  yearOfProduction: Date
  registrationNumber: string
  price: number


  constructor(brand: string, model: string, yearOfProduction: Date, registrationNumber: string, price: number) {
    this.brand = brand;
    this.model = model;
    this.yearOfProduction = yearOfProduction;
    this.registrationNumber = registrationNumber;
    this.price = price;
  }


}
