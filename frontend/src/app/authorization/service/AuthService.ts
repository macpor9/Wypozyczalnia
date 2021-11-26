import {Injectable, OnInit} from "@angular/core";
import {FacebookLoginProvider, GoogleLoginProvider, SocialAuthService, SocialUser} from "angularx-social-login";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {LoginResponse} from "../models/LoginResponse";
import {map} from "rxjs/operators";
import {Router} from "@angular/router";
import {UrlConstants} from "../../utils/UrlConstants";
import {RegisterResponse} from "../models/RegisterResponse";
import {User} from "../models/User";
import {UserService} from "../../application/service/UserService";

@Injectable({providedIn: 'root'})
export class AuthService implements OnInit {
  socialUser!: SocialUser;
  isLoggedIn: boolean = false;

  message!: string;
  successful!: boolean;

  HOME_PAGE = UrlConstants.HOME_PAGE
  REGISTER_PAGE = UrlConstants.REGISTER_PAGE
  LOGIN_PAGE = UrlConstants.LOGIN_PAGE


  constructor(
    private socialAuthService: SocialAuthService,
    private http: HttpClient,
    private router: Router,
    private userService: UserService
  ) {}

  ngOnInit() {
    this.socialAuthService.authState.subscribe((user) => {
      this.socialUser = user;
      this.isLoggedIn = (user != null);
    });
  }

  register(user: User) {
    return this.http.post<RegisterResponse>(environment.apiUrl + REGISTER_URL, user)
      .toPromise().then(
        data => {
          this.message = data.message
          this.successful = data.success
        },
        data => {
          this.message = data.message
          this.successful = false
        })
  }

  login(email: string, password: string) {
    return this.http.post<LoginResponse>(environment.apiUrl + SIGNIN_URL, {email, password})
      .pipe(map(response => {
        localStorage.setItem(ACCESS_TOKEN_KEY, response.accessToken)
        this.userService.setAccountData()
      })).toPromise().then(() => {
        console.log("success")
        this.router.navigate([this.HOME_PAGE])
      }, e => console.log("error" + e));
  }

  facebookLogin() {
    this.socialAuthService.signIn(FacebookLoginProvider.PROVIDER_ID, {prompt: 'consent'}).then(e => {
      console.log(e)
      this.http.post<LoginResponse>(environment.apiUrl + FACEBOOK_SIGNIN_URL, {accessToken: e.authToken})
        .pipe(map(response => {
          localStorage.setItem(ACCESS_TOKEN_KEY, response.accessToken)
          this.userService.setAccountData()
        })).toPromise()
        .then(() => {
          console.log("success")
          this.router.navigate([this.HOME_PAGE])

        }, e => console.log("error" + e));
    }, e => console.log(e))
  }

  googleLogin() {
    this.socialAuthService.signIn(GoogleLoginProvider.PROVIDER_ID, {prompt: 'consent'}).then(e => {
      console.log(e)
      this.http.post<LoginResponse>(environment.apiUrl + GOOGLE_SIGNIN_URL, {accessToken: e.authToken})
        .pipe(map(response => {
          localStorage.setItem(ACCESS_TOKEN_KEY, response.accessToken)
          this.userService.setAccountData()
        })).toPromise()
        .then(() => {
          console.log("success")
          this.router.navigate([this.HOME_PAGE])
        }, e => console.log("error" + e));
    }, e => console.log(e))
  }


  signOut() {
    localStorage.removeItem(ACCESS_TOKEN_KEY);
    this.socialAuthService.signOut();
    this.router.navigate([this.LOGIN_PAGE])
  }


}

const ACCESS_TOKEN_KEY = "accessToken";
const FACEBOOK_SIGNIN_URL = "/auth/facebook/signin";
const GOOGLE_SIGNIN_URL = "/auth/google/signin"
const SIGNIN_URL = "/auth/signin";
const REGISTER_URL = "/auth/users";
