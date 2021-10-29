import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AuthService} from "../service/AuthService";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.sass']
})


export class LoginComponent implements OnInit {

  loginForm!: FormGroup;



  constructor(
    private formBuilder: FormBuilder,
    public authService: AuthService
  ) {
    console.log("login page")
    console.log(authService.isLoggedIn)
  }

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      email: ['', Validators.required],
      password: ['', Validators.required]
    });


  }

  login(email: string, password: string): void {
    console.log("my login")
    this.authService.login(email, password);
  }

  loginWithFacebook(): void {
    this.authService.facebookLogin();
  }

  loginWithGoogle(): void {
    this.authService.googleLogin();
  }

  signOut(): void {
    this.authService.signOut();
  }

}
