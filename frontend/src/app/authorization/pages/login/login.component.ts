import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AuthService} from "../../service/AuthService";
import {User} from "../../models/User";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['../../authForm.sass', "login.component.sass"]
})


export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  user: User = new User("","","","","")


  constructor(
    private formBuilder: FormBuilder,
    public authService: AuthService,
  ) {
  }

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      email: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  login(email: string, password: string): void {
    console.log("my login")
    this.authService.login(email, password)
    console.log(this.authService.message)
  }


}
