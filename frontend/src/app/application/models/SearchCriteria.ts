export class SearchCriteria {
  brand: string | undefined;
  model: string | undefined;
  yearOfProductionFrom: number | undefined;
  yearOfProductionTo: number | undefined;
  registrationNumber: string | undefined;
  priceFrom: number | undefined;
  priceTo: number | undefined;
  available: boolean;

  constructor(brand: string | undefined = undefined,
              model: string | undefined = undefined,
              yearOfProductionFrom: number | undefined = 1900,
              yearOfProductionTo: number | undefined = Date.now(),
              registrationNumber: string | undefined = undefined,
              priceFrom: number | undefined = 0,
              priceTo: number | undefined = 10000000
  ) {
    this.brand = brand;
    this.model = model;
    this.yearOfProductionFrom = yearOfProductionFrom;
    this.yearOfProductionTo = yearOfProductionTo;
    this.registrationNumber = registrationNumber;
    this.priceFrom = priceFrom;
    this.priceTo = priceTo;
    this.available = false;
  }

  toString(){
    return "brand: " + this.brand + "\n" +
            "model: " + this.model + "\n" +
            "yearOfProductionFrom: " + this.yearOfProductionFrom + "\n" +
            "yearOfProductionTo: " + this.yearOfProductionTo + "\n" +
            "registrationNumber: " + this.registrationNumber + "\n" +
            "priceFrom: " + this.priceFrom + "\n" +
            "priceTo: " + this.priceTo + "\n" +
            "available: " + this.available

  }
}
