import {Component, OnInit, ViewChild} from "@angular/core";
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
  retailerPhoto: any;

  @ViewChild('fileInput') fileInput: any

  constructor(private router: Router,
              private rest: RestDataSource,
              private cookieService: CookieService) {
  }

  submitProfile(form: NgForm) {
    this.submitted = true;
    if (form.valid) {
      this.rest.editRetailer(this.retailer).subscribe(() =>
        this.router.navigate(['/store']))
      this.submitted = false;
    }
  }

  deleteProfile() {
    this.rest.deleteRetailer().subscribe({
      next: () => this.logout()
    })
  }

  logout() {
    this.cookieService.delete("jwtToken", '/', 'localhost')
    this.router.navigate(['/store']);
  }

  ngOnInit(): void {
    this.rest.getRetailer(this.decodeJwt()['jti']).subscribe(r => {
      let data = JSON.parse(JSON.stringify(r.body))
      this.retailer.fullName = data.fullName
      this.retailer.email = data.email
      this.retailer.birthDate = data.birthDate
      this.rest.getRetailerPhoto().subscribe(data => {
        if (data.size != 0) {
          const reader = new FileReader();
          reader.onloadend = () => {
            this.retailerPhoto = reader.result;
          };
          reader.readAsDataURL(data);
        } else {
          this.retailerPhoto = 'assets/default_retailer.jpg';
        }
      })
    })
  }

  decodeJwt(): any {
    try {
      return jwtDecode(document.cookie.substring('jwtToken='.length));
    } catch (error) {
      console.error('Error decoding or verifying token:', error);
    }
  }

  onFileSelected(event: any) {
    let formData: FormData = new FormData();
    formData.append("file", event.target.files[0]);
    this.rest.saveRetailerPhoto(formData).subscribe(() => {
      const reader = new FileReader();
      reader.onloadend = () => {
        this.retailerPhoto = reader.result;
      };
      reader.readAsDataURL(event.target.files[0]);
      }
    );
  }

  deletePhoto() {
    this.rest.deleteRetailerPhoto().subscribe(() => {
      this.retailerPhoto = 'assets/default_retailer.jpg';
      this.fileInput.nativeElement.value = '';
    })
  }
}
