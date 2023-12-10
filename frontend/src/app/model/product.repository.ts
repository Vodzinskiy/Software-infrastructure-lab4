import {Injectable} from "@angular/core";
import {Product} from "./product.model";
import {StaticDataSource} from "./static.datasource";
import {RestDataSource} from "./rest.datasource";

@Injectable()
export class ProductRepository {
  private products: Product[] = [];


  constructor(private dataSource: RestDataSource) {
    dataSource.getProducts().subscribe(data => {
      console.log(data)
      this.products = data;
    });
  }

  getProducts(): Product[] {
    return this.products;
  }
}
