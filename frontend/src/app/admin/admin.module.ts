import {NgModule} from "@angular/core";
import {ModelModule} from "../model/model.module";
import {BrowserModule} from "@angular/platform-browser";
import {FormsModule} from "@angular/forms";
import {RouterModule} from "@angular/router";
import {LoginComponent} from "./login.component";
import {RestDataSource} from "../model/rest.datasource";
import {SignupComponent} from "./signup.component";
import {ProfileComponent} from "./profile.component";



@NgModule({
  imports: [ModelModule, BrowserModule, FormsModule, RouterModule],
  declarations: [LoginComponent, SignupComponent, ProfileComponent],
  providers: [RestDataSource],
  exports: []
})
export class AdminModule {

}
