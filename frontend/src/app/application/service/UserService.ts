import {Account} from "../../authorization/models/Account";
import {environment} from "../../../environments/environment";
import {map} from "rxjs/operators";
import {Role} from "../../authorization/models/Role";
import {Injectable, OnInit} from "@angular/core";
import {SocialAuthService} from "angularx-social-login";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Constants} from "../../utils/Constants";
import {AuthorizationUtil} from "../../utils/AuthorizationUtil";

@Injectable({providedIn: 'root'})
export class UserService implements OnInit {
  constructor(
    private socialAuthService: SocialAuthService,
    private http: HttpClient,
  ){}

  ngOnInit() {
  }

  setAccountData() {

    return this.http.get<Account>(
      environment.apiUrl + Constants.ACCOUNT_DETAILS_URL,
      {headers: AuthorizationUtil.getJsonHeaders()})
      .pipe(map(response => {
        console.log("setting")
        localStorage.setItem("account", JSON.stringify(response))
      }))
  }

  hasRole(role: string): boolean {
    let account: Account = JSON.parse(localStorage.getItem('account') || "")
    return account.roles.includes(role)
  }
}

