import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from "./application/pages/home/home.component";
import {LoginComponent} from "./authorization/pages/login/login.component";
import {NotFoundComponent} from "./application/pages/not-found/not-found.component";
import {UrlConstants} from "./utils/UrlConstants";
import {RegisterComponent} from "./authorization/pages/register/register.component";

const routes: Routes = [
  {path: UrlConstants.HOME_PAGE.slice(1), component: HomeComponent},
  {path: UrlConstants.LOGIN_PAGE.slice(1), component: LoginComponent},
  {path: UrlConstants.REGISTER_PAGE.slice(1), component: RegisterComponent},


  {path: '**', component: NotFoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
