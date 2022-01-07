export class CarResponse {
  model: string
  brand: string
  yearOfProduction: Date
  price: number
  registrationNumber: string
  available: boolean
  availableDate: Date


  constructor(model: string = "",
              brand: string = "",
              yearOfProduction: Date = new Date(),
              price: number = 0,
              registrationNumber: string = "",
              available: boolean = true,
              availableDate: Date = new Date()) {
    this.model = model;
    this.brand = brand;
    this.yearOfProduction = yearOfProduction;
    this.price = price;
    this.registrationNumber = registrationNumber;
    this.available = available;
    this.availableDate = availableDate;
  }

  toString(){
    return "model: " + this.model + "\n" +
     "brand" + this.brand + "\n" +
     "yearOfProduction" + this.yearOfProduction + "\n" +
     "price" + this.price + "\n" +
     "registrationNumber" + this.registrationNumber + "\n" +
     "available" + this.available + "\n" +
     "availableDate" + this.availableDate
  }
}
