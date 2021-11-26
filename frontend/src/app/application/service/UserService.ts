import {Account} from "../../authorization/models/Account";
import {environment} from "../../../environments/environment";
import {map} from "rxjs/operators";
import {Role} from "../../authorization/models/Role";
import {Injectable, OnInit} from "@angular/core";
import {SocialAuthService} from "angularx-social-login";
import {HttpClient, HttpHeaders} from "@angular/common/http";

@Injectable({providedIn: 'root'})
export class UserService implements OnInit {



  constructor(
    private socialAuthService: SocialAuthService,
    private http: HttpClient,
  ){
  }

  ngOnInit() {
  }

  setAccountData() {
    let headers: HttpHeaders = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${localStorage.getItem(ACCESS_TOKEN_KEY)}`
    })
    this.http.get<Account>(
      environment.apiUrl + ACCOUNT_DETAILS_URL,
      {headers: headers})
      .pipe(map(response => {
        console.log("setting")
        localStorage.setItem("account", JSON.stringify(response))
      })).toPromise().then((r) => console.log("success: " + r), (e) => console.log("error: " + e))
  }

  hasRole(role: string): boolean {
    let account: Account = JSON.parse(localStorage.getItem('account') || "")
    return account.roles.includes(role)
  }
}
const ACCESS_TOKEN_KEY = "accessToken";
const ACCOUNT_DETAILS_URL = "/users/me";

