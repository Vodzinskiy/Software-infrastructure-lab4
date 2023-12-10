import {Component, OnInit} from "@angular/core";
import {Retailer} from "../model/retailer.model";
import {Router} from "@angular/router";
import {RestDataSource} from "../model/rest.datasource";
import {NgForm} from "@angular/forms";
import {jwtDecode} from "jwt-decode";
import {CookieService} from "ngx-cookie-service";

@Component({
  selector: "profile",
  templateUrl: "profile.component.html"
})
export class ProfileComponent implements OnInit{
  submitted: boolean = false;
  retailer: Retailer = new Retailer();
  textError: string = "";

  constructor(private router: Router,
              private rest: RestDataSource,
              private cookieService: CookieService) {
  }

  submitProfile(form: NgForm) {
    this.submitted = true;
    if (form.valid) {

      this.submitted = false;
    }
  }

  deleteProfile() {
    this.rest.deleteRetailer().subscribe(() =>
      this.logout())
  }

  logout() {
    this.cookieService.delete("jwtToken", '/', 'localhost')
    this.router.navigate(['/store']);
  }

  ngOnInit(): void {
    this.retailer.fullName = this.decodeJwt()['name']
    this.retailer.email = this.decodeJwt()['sub']
    this.retailer.birthDate = this.decodeJwt()['birthDate']
  }

  decodeJwt(): any {
    try {
      return jwtDecode(document.cookie.substring('jwtToken='.length));
    } catch (error) {
      console.error('Error decoding or verifying token:', error);
    }
  }


}
