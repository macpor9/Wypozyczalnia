import {SortCriteria} from "../application/models/SortCriteria";

export class SortUtil {
  public static SORT_OPTIONS: SortCriteria[] = [
    new SortCriteria("price", "ascending", "price: ascending"),
    new SortCriteria("price", "descending", "price: descending"),
    new SortCriteria("brand", "ascending", "brand: ascending"),
    new SortCriteria("brand", "descending", "brand: descending"),
    new SortCriteria("model", "ascending", "model: ascending"),
    new SortCriteria("model", "descending", "model: descending"),
    new SortCriteria("yearOfProduction", "ascending", "year of production: ascending"),
    new SortCriteria("yearOfProduction", "descending", "year of production: descending")
  ]
}
