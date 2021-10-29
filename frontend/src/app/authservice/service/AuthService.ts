import {Injectable, OnInit} from "@angular/core";
import {FacebookLoginProvider, GoogleLoginProvider, SocialAuthService, SocialUser} from "angularx-social-login";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {LoginResponse} from "../models/LoginResponse";
import {map} from "rxjs/operators";

@Injectable({providedIn: 'root'})
export class AuthService implements OnInit {
  socialUser!: SocialUser;
  isLoggedIn: boolean = false;
  username!: string;
  password!: string;


  constructor(
    private socialAuthService: SocialAuthService,
    private http: HttpClient
  ) {
  }

  ngOnInit() {
    this.socialAuthService.authState.subscribe((user) => {
      this.socialUser = user;
      this.isLoggedIn = (user != null);
    });
  }

  login(username: string, password: string) {
    return this.http.post<LoginResponse>(environment.apiUrl + SIGNIN_URL, {username, password})
      .pipe(map(response => {
        localStorage.setItem(ACCESS_TOKEN_KEY, response.accessToken)
      })).toPromise().then(e => console.log("success"), e => console.log("error" + e));
  }

  facebookLogin() {
    this.socialAuthService.signIn(FacebookLoginProvider.PROVIDER_ID, {prompt: 'consent'}).then(e => {
      console.log(e)
      this.http.post<LoginResponse>(environment.apiUrl + FACEBOOK_SIGNIN_URL, {accessToken: e.authToken})
        .pipe(map(response => {
          console.log(response)
          localStorage.setItem(ACCESS_TOKEN_KEY, response.accessToken)
        })).toPromise()
        .then(e => console.log("success"), e => console.log("error" + e));
    }, e => console.log(e))
  }

  googleLogin() {
    this.socialAuthService.signIn(GoogleLoginProvider.PROVIDER_ID, {prompt: 'consent'}).then(e => {
      console.log(e)
      this.http.post<LoginResponse>(environment.apiUrl + GOOGLE_SIGNIN_URL, {accessToken: e.authToken})
        .pipe(map(response => {
          console.log("sddsdsds")
          localStorage.setItem(ACCESS_TOKEN_KEY, response.accessToken)
        })).toPromise()
        .then(e => console.log("success"), e => console.log("error" + e));
    }, e => console.log(e))
  }


  signOut() {
    localStorage.removeItem(ACCESS_TOKEN_KEY);
    this.socialAuthService.signOut();

  }
}

const ACCESS_TOKEN_KEY = "accessToken";
const FACEBOOK_SIGNIN_URL = "/facebook/signin";
const GOOGLE_SIGNIN_URL = "/google/signin"
const SIGNIN_URL = "/signin";
