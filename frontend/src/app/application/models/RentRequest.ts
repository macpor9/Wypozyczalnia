export class RentRequest{
  reservedFrom: Date
  reservedUntil: Date


  constructor(reservedFrom: Date = new Date(), reservedUntil: Date = new Date()) {
    this.reservedFrom = reservedFrom;
    this.reservedUntil = reservedUntil;
  }

}
