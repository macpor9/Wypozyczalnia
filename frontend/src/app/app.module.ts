import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HomeComponent} from './application/pages/home/home.component';
import {LoginComponent} from './authorization/pages/login/login.component';
import {
  FacebookLoginProvider,
  GoogleLoginProvider,
  SocialAuthServiceConfig,
  SocialLoginModule
} from 'angularx-social-login';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {environment} from "../environments/environment";
import {NotFoundComponent} from './application/pages/not-found/not-found.component';
import {HttpClientModule} from "@angular/common/http";
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {RegisterComponent} from './authorization/pages/register/register.component';
import {FacebookLoginButtonComponent} from './authorization/components/facebook-login-button/facebook-login-button.component';
import {GoogleLoginButtonComponent} from './authorization/components/google-login-button/google-login-button.component';
import {AdminPanelComponent} from './application/pages/admin-panel/admin-panel.component';
import {TopBarComponent} from './application/components/top-bar/top-bar.component';
import {ProfileComponent} from './application/pages/profile/profile.component';
import {AddCarPopupComponent} from './application/components/add-car-popup/add-car-popup.component';
import { CarComponent } from './application/components/car/car.component';
import { FilterWindowComponent } from './application/components/filter-window/filter-window.component';
import { CarHistoryComponent } from './application/components/car-history/car-history.component';
import { RentHistoryDetailsComponent } from './application/components/rent-history-details/rent-history-details.component';


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent,
    NotFoundComponent,
    RegisterComponent,
    FacebookLoginButtonComponent,
    GoogleLoginButtonComponent,
    AdminPanelComponent,
    TopBarComponent,
    ProfileComponent,
    AddCarPopupComponent,
    CarComponent,
    FilterWindowComponent,
    CarHistoryComponent,
    RentHistoryDetailsComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    SocialLoginModule,
    HttpClientModule,
    NgbModule,
    FormsModule
  ],
  providers: [
    {
      provide: 'SocialAuthServiceConfig',
      useValue: {
        autoLogin: false,
        providers: [
          {
            id: FacebookLoginProvider.PROVIDER_ID,
            provider: new FacebookLoginProvider(
              environment.facebookAppId
            )
          },
          {
            id: GoogleLoginProvider.PROVIDER_ID,
            provider: new GoogleLoginProvider(
              environment.googleAppId
            )
          }
        ]
      } as SocialAuthServiceConfig,
    }],
  bootstrap: [AppComponent]
})
export class AppModule {
}
