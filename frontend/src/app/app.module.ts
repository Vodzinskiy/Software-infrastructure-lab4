import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {AppComponent} from './app.component';
import {StoreModule} from "./store/store.module";
import {StoreComponent} from "./store/store.component";
import {CheckoutComponent} from "./store/checkout.component";
import {CartDetailComponent} from "./store/cartDetail.component";
import {RouterModule} from "@angular/router";
import {StoreFirstGuard} from "./storeFirst.guard";
import {AdminModule} from "./admin/admin.module";
import {LoginComponent} from "./admin/login.component";
import {SignupComponent} from "./admin/signup.component";
import {ProfileComponent} from "./admin/profile.component";

@NgModule({
  declarations: [AppComponent],
  imports: [BrowserModule, StoreModule, AdminModule,
    RouterModule.forRoot([
      {
        path: "store", component: StoreComponent,
        canActivate: [StoreFirstGuard]
      },
      {
        path: "cart", component: CartDetailComponent,
        canActivate: [StoreFirstGuard]
      },
      {
        path: "checkout", component: CheckoutComponent,
        canActivate: [StoreFirstGuard]
      },
      {
        path: "login", component: LoginComponent,
      },
      {
        path: "signup", component: SignupComponent,
      },
      {
        path: "profile", component: ProfileComponent,
      },

      {path: "**", redirectTo: "/store"}
    ])],
  providers: [StoreFirstGuard],
  bootstrap: [AppComponent]
})
export class AppModule {
}
