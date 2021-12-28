export class SortCriteria {
  public field: string;
  public mode: string;
  public name: string;

  constructor(field: string = "price",
              mode: string = "ascending",
              name: string = "price ascending"
  ) {
    this.field = field;
    this.mode = mode
    this.name = name;
  }

}
