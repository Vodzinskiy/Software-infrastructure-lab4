import {Injectable} from "@angular/core";
import {Cart, CartLine} from "./cart.model";

@Injectable()
export class Order {
  public id?: number;
  public fullName?: string;
  public address?: string;
  public birthDate?: string;

  public products: CartLine[];


  constructor(cart: Cart) {
    this.products = cart.products
  }

  clear() {
    this.id = undefined;
    this.fullName = this.address = this.birthDate = undefined;
    //this.cart.clear();
  }
}
