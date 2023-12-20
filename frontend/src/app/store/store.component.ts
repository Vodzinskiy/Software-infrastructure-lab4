import {Component, OnInit} from "@angular/core";
import {Product} from "../model/product.model";
import {Cart} from "../model/cart.model";
import {ActivatedRoute, Router} from "@angular/router";
import {RestDataSource} from "../model/rest.datasource";
import {SortService} from "../model/sort.service";
import {Retailer} from "../model/retailer.model";

@Component({
  selector: "store",
  templateUrl: "store.component.html"
})
export class StoreComponent implements OnInit {
  productsPerPage = 4;
  selectedPage = 1;
  isRetailer: boolean = false;
  sort: string = "alphabetically";
  products: Product[] = [];
  pageCount: number = 0;
  retailerPage: boolean = false;
  retailerPhoto: any;
  retailer: Retailer = new Retailer();

  constructor(private rest: RestDataSource,
              private cart: Cart,
              private router: Router,
              private sortService: SortService,
              private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.retailerPage = false;
    if (document.cookie.split(';').some((item) => item.trim().startsWith('jwtToken='))) {
      this.isRetailer = true
    }
    this.onSortChange()
  }

  onSortChange() {
    this.pageCountGenerate();
    let id = this.route.snapshot.paramMap.get("id")
    let pageIndex = (this.selectedPage - 1) * this.productsPerPage;
    this.rest.getProducts().subscribe((data: Product[]) => {
      if (id !== null) {
        data = data.filter(product => product.retailerID === id)
        this.retailerPage = true;
        this.getRetailerInfo(id)
      }
      if (this.sort === "low-to-high") {
        data = this.sortService.sortLowToHigh(data);
      } else if (this.sort === "high-to-low") {
        data = this.sortService.sortHighToLow(data);
      } else {
        data = this.sortService.sortProductsByTitle(data);
      }
      this.products = data.slice(pageIndex, pageIndex + this.productsPerPage);
      this.loadImages();
    });
  }

  getRetailerInfo(id: string) {
    this.rest.getRetailer(id).subscribe(r =>{
      let data = JSON.parse(JSON.stringify(r.body))
      this.retailer.fullName = data.fullName
      this.retailer.email = data.email
    })
    this.rest.getRetailerPhoto().subscribe(data => {
      if (data.size != 0) {
        const reader = new FileReader();
        reader.onloadend = () => {
          this.retailerPhoto = reader.result;
        };
        reader.readAsDataURL(data);
      } else {
        this.retailerPhoto = 'assets/default_retailer.jpg';
      }
    })
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
    this.rest.getProducts().subscribe(data => {
      if (this.retailerPage) {
        data = data.filter(product => product.retailerID === this.route.snapshot.paramMap.get("id"))
      }
      this.pageCount = Math.ceil(data.length / this.productsPerPage)
    })

  }

  addProductToCart(product: Product) {
    this.cart.addLine(product);
    this.router.navigateByUrl("/cart");
  }

  loadImages() {
    let id = null
    if (this.retailerPage) {
      id = this.products[0].retailerID
    }
    this.rest.getAllImages(id).subscribe(res => {
      for (const img of res.body as Image[]) {
        const product = this.products.find(p => img.id === p.id);
        if (product) {
          const bytes = atob(img.image);
          const ab = new ArrayBuffer(bytes.length);
          const ia = new Uint8Array(ab);
          for (let i = 0; i < bytes.length; i++) {
            ia[i] = bytes.charCodeAt(i);
          }
          const reader = new FileReader();
          reader.onloadend = () => {
            product.productPhoto = reader.result;
          };
          reader.readAsDataURL(new Blob([ab], {type: 'application/octet-stream'}));
        }
      }
    });
  }
}
interface Image {
  id: string;
  image: any;
}
