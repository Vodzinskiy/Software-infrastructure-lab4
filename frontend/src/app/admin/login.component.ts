import {Component} from "@angular/core";
import {Retailer} from "../model/retailer.model";
import {NgForm} from "@angular/forms";
import {RestDataSource} from "../model/rest.datasource";
import {Router} from "@angular/router";
import {catchError, EMPTY} from "rxjs";

@Component({
  selector: "login",
  templateUrl: "login.component.html"
})
export class LoginComponent {
  submitted: boolean = false;
  retailer: Retailer = new Retailer();
  textError: string = "";

  constructor(private router: Router, private rest: RestDataSource) {
  }


  submitLogin(form: NgForm) {
    this.submitted = true;
    if (form.valid) {
      this.rest.loginRetailer(this.retailer).pipe(
        catchError((error) => {
          if (error.status === 401) {
            this.textError = "Incorrect login or password";
          }
          return EMPTY;
        })
      ).subscribe(
        data => {
          this.router.navigate(['/store']);
        }
      );

      this.submitted = false;
    }
  }
}
