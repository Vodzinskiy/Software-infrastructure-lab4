import {Injectable} from "@angular/core";
import {Product} from "./product.model";
import {StaticDataSource} from "./static.datasource";

@Injectable()
export class ProductRepository {
  private products: Product[] = [];
  private categories: string[] = [];

  constructor(private dataSource: StaticDataSource) {
    dataSource.getProducts().subscribe(data => {
      this.products = data;
    });
  }

  getProducts(category?: string): Product[] {
    return this.products;
  }


  getCategories(): string[] {
    return this.categories;
  }
}
