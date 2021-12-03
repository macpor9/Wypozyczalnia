import {HttpHeaders} from "@angular/common/http";
import {Constants} from "./Constants";

export class ServiceUtil {
  constructor() {
  }

  public onSuccess(v: any){
    console.log("success" + v)
  }

  public onFailure(v: any){
    console.log("error" + v)
  }
}
