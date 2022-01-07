export class RentHistory{
  reservedFrom: Date
  reservedUntil: Date
  price: number
  registrationNumber: string

  constructor(registrationNumber: string = "", reservedFrom: Date = new Date(), reservedUntil: Date = new Date(), price: number = 0) {
    this.registrationNumber = registrationNumber;
    this.reservedFrom = reservedFrom;
    this.reservedUntil = reservedUntil;
    this.price = price;
  }

  public getReservationDays() {
      let from = new Date(this.reservedFrom)
      let until = new Date(this.reservedUntil)
      let time = until.getTime() - from.getTime()
      return Math.ceil((time / (1000 * 3600 * 24) + 1))
  }

}
