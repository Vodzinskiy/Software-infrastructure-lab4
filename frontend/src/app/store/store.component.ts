import {Component, OnInit} from "@angular/core";
import {Product} from "../model/product.model";
import {ProductRepository} from "../model/product.repository";
import {Cart} from "../model/cart.model";
import {Router} from "@angular/router";
import {RestDataSource} from "../model/rest.datasource";


@Component({
  selector: "store",
  templateUrl: "store.component.html"
})
export class StoreComponent implements OnInit {
  productsPerPage = 4;
  selectedPage = 1;
  retailer: boolean = false;
  sort: string = "alphabetically"
  products: Product[] = [];
  pageCount: number = 0;


  constructor(private rest: RestDataSource,
              private cart: Cart,
              private router: Router) {
  }

  ngOnInit(): void {
    if (document.cookie.split(';').some((item) => item.trim().startsWith('jwtToken='))) {
      this.retailer = true
    }
    this.onSortChange()

  }

  onSortChange() {
    this.pageCountGenerate();
    let pageIndex = (this.selectedPage - 1) * this.productsPerPage;

    this.rest.getProducts().subscribe((data: Product[]) => {

      if (this.sort === "low-to-high") {
        data = this.sortLowToHigh(data);
      } else if (this.sort === "high-to-low") {
        data = this.sortHighToLOw(data);
      } else {
        data = this.sortProductsByTitle(data);
      }
      this.products = data.slice(pageIndex, pageIndex + this.productsPerPage);
    });
  }


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
  sortHighToLOw(products: Product[]) {
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


  changePage(newPage: number) {
    this.selectedPage = newPage;
    this.onSortChange()
  }

  changePageSize(newSize: number) {
    this.productsPerPage = Number(newSize);
    this.changePage(1);
    this.onSortChange()
  }


  pageCountGenerate() {
    this.rest.getProducts().subscribe(data =>
    this.pageCount = Math.ceil(data.length / this.productsPerPage))
  }

  addProductToCart(product: Product) {
    this.cart.addLine(product);
    this.router.navigateByUrl("/cart");
  }
}
