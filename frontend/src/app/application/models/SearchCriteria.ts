export class SearchCriteria {
  brand: string | undefined;
  model: string | undefined;
  yearOfProductionFrom: number | undefined;
  yearOfProductionTo: number | undefined;
  registrationNumber: string | undefined;
  priceFrom: number | undefined;
  priceTo: number | undefined;
  available: boolean;

  public static createEmptySearchCriteria(){
    return new SearchCriteria(undefined,undefined,0,2021,undefined,0,10000000)
  }


  constructor(brand: string | undefined, model: string | undefined, yearOfProductionFrom: number | undefined, yearOfProductionTo: number | undefined,
              registrationNumber: string | undefined, priceFrom: number | undefined, priceTo: number | undefined) {
    this.brand = brand;
    this.model = model;
    this.yearOfProductionFrom = yearOfProductionFrom;
    this.yearOfProductionTo = yearOfProductionTo;
    this.registrationNumber = registrationNumber;
    this.priceFrom = priceFrom;
    this.priceTo = priceTo;
    this.available = true;
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
