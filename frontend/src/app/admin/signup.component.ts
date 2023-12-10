import {Component} from "@angular/core";
import {Retailer} from "../model/retailer.model";
import {Router} from "@angular/router";
import {RestDataSource} from "../model/rest.datasource";
import {NgForm} from "@angular/forms";

@Component({
  selector: "signup",
  templateUrl: "signup.component.html"
})
export class SignupComponent {
  submitted: boolean = false;
  retailer: Retailer = new Retailer();
  textError: string = "";

  constructor(private router: Router, private rest: RestDataSource) {
  }

  submitSignup(form: NgForm) {
    this.submitted = true;
    if (form.valid) {
      this.rest.signupRetailer(this.retailer).subscribe(
        data => {
          this.router.navigate(['/store']);
        }
      );
      this.submitted = false;
    }
  }
}
