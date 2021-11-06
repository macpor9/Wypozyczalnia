import { Component, OnInit } from '@angular/core';
import {AuthService} from "../../service/AuthService";

@Component({
  selector: 'app-facebook-login-button',
  templateUrl: './facebook-login-button.component.html',
  styleUrls: ['./facebook-login-button.component.sass', '../login-buttons.sass']
})
export class FacebookLoginButtonComponent implements OnInit {

  constructor(public authService: AuthService) { }

  ngOnInit(): void {
  }

  loginWithFacebook(): void {
    this.authService.facebookLogin();
  }

}
