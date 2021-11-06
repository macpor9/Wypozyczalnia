import { Component, OnInit } from '@angular/core';
import {AuthService} from "../../service/AuthService";

@Component({
  selector: 'app-google-login-button',
  templateUrl: './google-login-button.component.html',
  styleUrls: ['./google-login-button.component.sass', '../login-buttons.sass']
})
export class GoogleLoginButtonComponent implements OnInit {

  constructor(public authService: AuthService) { }

  ngOnInit(): void {
  }

  loginWithGoogle(): void {
    this.authService.googleLogin();
  }
}
