import { Component, OnInit } from '@angular/core';
import {AuthService} from "../../service/AuthService";
import {FormBuilder, FormGroup} from "@angular/forms";
import {User} from "../../models/User";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['../../authForm.sass']
})
export class RegisterComponent implements OnInit {
  registerForm!: FormGroup;
  user: User = new User("","","","","")


  constructor(
    private formBuilder: FormBuilder,
    public authService: AuthService
  ) { }

  ngOnInit() {
    this.registerForm = this.formBuilder.group({
    });
  }

  register(user: User): void {
    this.authService.register(user)
  }

}
