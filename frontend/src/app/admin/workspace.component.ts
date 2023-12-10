import {Component, OnInit} from "@angular/core";
import {RestDataSource} from "../model/rest.datasource";
import {Product} from "../model/product.model";
import {Order} from "../model/order.model";
import {HttpResponse} from "@angular/common/http";

@Component({
  selector: "workspace",
  templateUrl: "workspace.component.html"
})
export class WorkspaceComponent implements OnInit {
  submitted: boolean = false;
  product: Product = new Product();
  orders: Order[] = [];
  products: Product[] = [];
  textError: string = "";

  constructor(private rest: RestDataSource) {
  }

  createProduct() {
    this.rest.saveProduct(this.product).subscribe(() =>
      this.loadProducts()
    )
  }


  ngOnInit(): void {
    this.loadOrders();
    this.loadProducts();
  }

  loadOrders() {
    this.rest.getOrders().subscribe((data: HttpResponse<Object>) => {
      this.orders = data.body as Order[];
    });
  }

  loadProducts() {
    this.rest.getProductsForRetailer().subscribe((data: HttpResponse<Object>) => {
      this.products = data.body as Product[];
    });
  }

  edit(id: string | undefined, title: string  | undefined, price: number  | undefined) {
    if (id) {
      let product: Product = new Product()
      product.price = price
      product.title = title
      this.rest.editProduct(id, product).subscribe()
    }
  }

  delete(id: string | undefined) {
    if (id) {
      this.rest.deleteProduct(id).subscribe(() =>
        this.loadProducts()
      )
    }

  }
}
