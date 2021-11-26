import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from "./application/pages/home/home.component";
import {LoginComponent} from "./authorization/pages/login/login.component";
import {NotFoundComponent} from "./application/pages/not-found/not-found.component";
import {UrlConstants} from "./utils/UrlConstants";
import {RegisterComponent} from "./authorization/pages/register/register.component";
import {AdminPanelComponent} from "./application/pages/admin-panel/admin-panel.component";
import {ProfileComponent} from "./application/pages/profile/profile.component";

const routes: Routes = [
  {path: UrlConstants.HOME_PAGE.slice(1), component: HomeComponent},
  {path: UrlConstants.LOGIN_PAGE.slice(1), component: LoginComponent},
  {path: UrlConstants.REGISTER_PAGE.slice(1), component: RegisterComponent},
  {path: UrlConstants.ADMIN_PAGE.slice(1), component: AdminPanelComponent},
  {path: UrlConstants.PROFILE_PAGE.slice(1), component: ProfileComponent},



  {path: '**', component: NotFoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
