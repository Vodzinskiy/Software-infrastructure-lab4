import {Injectable} from "@angular/core";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {Product} from "./product.model";
import {Order} from "./order.model";
import {Retailer} from "./retailer.model";
import {env} from "../../environments/environments";


@Injectable()
export class RestDataSource {
  baseUrl: string;

  constructor(private http: HttpClient) {
    this.baseUrl = env.BACKEND_URL;
  }

  getProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(this.baseUrl + "product");
  }

  saveOrder(order: Order): Observable<Order> {
    return this.http.post<Order>(this.baseUrl + "order", order);
  }

  loginRetailer(retailer: Retailer) {
    return this.http.post<Retailer>(this.baseUrl + "retailer/login", retailer, {
      observe: 'response',
      withCredentials: true
    });
  }

  signupRetailer(retailer: Retailer) {
    return this.http.post<Retailer>(this.baseUrl + "retailer/signup", retailer, {
      observe: 'response',
      withCredentials: true
    });
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
    return this.http.get(this.baseUrl + "order", {
      observe: 'response',
      withCredentials: true
    });
  }

  saveProduct(product: Product) {
    return this.http.post(this.baseUrl + "product", product, {observe: 'response', withCredentials: true})
  }

  getProductsForRetailer() {
    return this.http.get(this.baseUrl + "retailer", {observe: 'response', withCredentials: true});
  }

  deleteProduct(id: string) {
    return this.http.delete(this.baseUrl + "product/" + id, {observe: 'response', withCredentials: true})
  }

  editProduct(id: string, product: Product) {
    return this.http.patch<Retailer>(this.baseUrl + "product/" + id, product, {
      observe: 'response',
      withCredentials: true
    });
  }

  deleteRetailerPhoto() {
    return this.http.delete(this.baseUrl + "retailer/photo", {observe: 'response', withCredentials: true});
  }

  saveRetailerPhoto(form: FormData) {
    return this.http.post(this.baseUrl + "retailer/photo", form, {observe: 'response', withCredentials: true});
  }

  getRetailerPhoto() {
    return this.http.get(this.baseUrl + "retailer/photo", {responseType: 'blob', withCredentials: true})
  }

  saveProductImg(form: FormData, id: string) {
    return this.http.post(this.baseUrl + "product/img/" + id, form, {observe: 'response', withCredentials: true});
  }

  deleteProductImg(id: string) {
    return this.http.delete(this.baseUrl + "product/img/" + id, {observe: 'response', withCredentials: true})
  }

  getAllImages(id: string | null | undefined){
    if (id === null || id === undefined) {
      id = ""
    }
    let params = new HttpParams();
    params = params.append('id', id);
    return this.http.get(this.baseUrl + "product/img", {params: params, observe: 'response', withCredentials: true})
  }
}


