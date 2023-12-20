import {Injectable} from "@angular/core";
import {Product} from "./product.model";

@Injectable()
export class SortService {
  sortLowToHigh(products: Product[]) {
    return products.sort((a, b) => {
      if (a.price! > b.price!) {
        return 1;
      }
      if (b.price! > a.price!) {
        return -1;
      }
      return 0;
    });
  }
  sortHighToLow(products: Product[]) {
    return products.sort((a, b) => {
      if (a.price! > b.price!) {
        return -1;
      }
      if (b.price! > a.price!) {
        return 1;
      }
      return 0;
    });
  }

  sortProductsByTitle(products: Product[]) {
    return products.sort((a, b) => {
      if (a.title!.toLowerCase() < b.title!.toLowerCase()) {
        return -1;
      }
      if (a.title!.toLowerCase() > b.title!.toLowerCase()) {
        return 1;
      }
      return 0;
    });
  }
}
