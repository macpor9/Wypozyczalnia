import {Injectable, OnInit} from "@angular/core";
import {FacebookLoginProvider, SocialAuthService, SocialUser} from "angularx-social-login";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";

@Injectable({providedIn: 'root'})
export class AuthService implements OnInit {
  socialUser!: SocialUser;
  isLoggedIn: boolean = false;


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

  facebookLogin() {
    this.socialAuthService.signIn(FacebookLoginProvider.PROVIDER_ID).then(e => {
      console.log(e)
      this.http.post(environment.apiUrl + "/facebook/signin", {accessToken: e.authToken}).subscribe(
        data => console.log('success', data),
        error => console.log('error', error)
      );

    }, e => console.log("error "))
  }

  signOut() {
    this.socialAuthService.signOut();

  }
}
