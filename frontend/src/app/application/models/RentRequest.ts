export class RentRequest{
  reservedFrom: Date
  reservedUntil: Date


  constructor(reservedFrom: Date, reservedUntil: Date) {
    this.reservedFrom = reservedFrom;
    this.reservedUntil = reservedUntil;
  }

  public static createEmptyRentRequest(){
    return new RentRequest(new Date(), new Date())
  }
}
