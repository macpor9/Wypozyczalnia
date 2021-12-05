import {HttpHeaders} from "@angular/common/http";
import {Constants} from "./Constants";

export class AuthorizationUtil {

  public static getJsonHeaders(){
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${localStorage.getItem(Constants.ACCESS_TOKEN_KEY)}`
    })
  }
  public static getMultipartHeaders(){
    return new HttpHeaders({
      'Authorization': `Bearer ${localStorage.getItem(Constants.ACCESS_TOKEN_KEY)}`
    })
  }

  static readonly jsonHeaders: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${localStorage.getItem(Constants.ACCESS_TOKEN_KEY)}`
  })

  static readonly multipartHeaders: HttpHeaders = new HttpHeaders({
    'Authorization': `Bearer ${localStorage.getItem(Constants.ACCESS_TOKEN_KEY)}`
  })

}
