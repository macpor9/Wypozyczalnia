import {Component, Input, OnInit, Output} from '@angular/core';
import {AuthService} from "../../../authorization/service/AuthService";
import {Constants} from "../../../utils/Constants";
import {UserService} from "../../service/UserService";
import {Role} from "../../../authorization/models/Role";

@Component({
  selector: 'app-top-bar',
  templateUrl: './top-bar.component.html',
  styleUrls: ['./top-bar.component.sass']
})
export class TopBarComponent implements OnInit {

  ADMIN_PAGE = Constants.ADMIN_PAGE
  PROFILE_PAGE = Constants.PROFILE_PAGE
  HOME_PAGE = Constants.HOME_PAGE

  @Input()
  @Output()
  opened: boolean = false

  constructor(public authService: AuthService, public userService: UserService) {
  }

  ngOnInit(): void {
  }


  signOut(): void {
    this.authService.signOut();
  }

  openPopup() {
    this.opened = true
  }


}
