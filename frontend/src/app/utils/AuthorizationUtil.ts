import {HttpHeaders} from "@angular/common/http";
import {Constants} from "./Constants";

export class AuthorizationUtil {

  static readonly jsonHeaders: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${localStorage.getItem(Constants.ACCESS_TOKEN_KEY)}`
  })

  static readonly multipartHeaders: HttpHeaders = new HttpHeaders({
    'Authorization': `Bearer ${localStorage.getItem(Constants.ACCESS_TOKEN_KEY)}`
  })

}
