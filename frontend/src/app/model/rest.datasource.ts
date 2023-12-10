import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {Product} from "./product.model";
import {Order} from "./order.model";
import {Retailer} from "./retailer.model";

const PROTOCOL = "http";
const PORT = 3500;

@Injectable()
export class RestDataSource {
  baseUrl: string;


  constructor(private http: HttpClient) {
    this.baseUrl = `http://localhost:8080/`;
  }

  getProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(this.baseUrl + "product");
  }

  saveOrder(order: Order): Observable<Order> {
    return this.http.post<Order>(this.baseUrl + "order", order);
  }

  loginRetailer(retailer: Retailer) {
    return this.http.post<Retailer>(this.baseUrl + "retailer/login", retailer, {observe: 'response',
      withCredentials: true});
  }

  signupRetailer(retailer: Retailer) {
    return this.http.post<Retailer>(this.baseUrl + "retailer/signup", retailer, {observe: 'response',
      withCredentials: true});
  }

  editRetailer(retailer: Retailer) {
    return this.http.patch<Retailer>(this.baseUrl + "retailer", retailer, {observe: 'response', withCredentials: true});
  }

  deleteRetailer() {
    return this.http.delete(this.baseUrl + "retailer", {observe: 'response', withCredentials: true});
  }

  getRetailer(id: string) {
    return this.http.get(this.baseUrl + "retailer/" + id, {observe: 'response', withCredentials: true});
  }
}


