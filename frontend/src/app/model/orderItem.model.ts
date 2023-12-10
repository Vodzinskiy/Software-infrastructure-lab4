import {Product} from "./product.model";

export class OrderItem {
  constructor(
    public quantity: number,
    public product: Product) {
  }
}
