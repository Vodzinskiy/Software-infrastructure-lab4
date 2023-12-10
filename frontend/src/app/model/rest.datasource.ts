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

  getOrders() {
    return this.http.get(this.baseUrl + "order", {observe: 'response',
      withCredentials: true});
  }

  saveProduct(product: Product ) {
    return this.http.post(this.baseUrl + "product", product, {observe: 'response', withCredentials: true})
  }

  getProductsForRetailer() {
    return this.http.get(this.baseUrl + "retailer", {observe: 'response',      withCredentials: true});
  }

  deleteProduct(id: string) {
    return this.http.delete(this.baseUrl + "product/" + id, {observe: 'response', withCredentials: true})
  }

  editProduct(id: string, product: Product) {
    return this.http.patch<Retailer>(this.baseUrl + "product/" + id, product, {observe: 'response', withCredentials: true});
  }
}


