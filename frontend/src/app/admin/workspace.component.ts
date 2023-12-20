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
      this.loadImages();
    });
  }

  edit(id: string | undefined, title: string | undefined, price: number | undefined) {
    if (id) {
      let product: Product = new Product()
      product.price = price
      product.title = title
      this.rest.editProduct(id, product).subscribe()
    }
  }

  delete(id: string | undefined) {
    if (id) {
      const index = this.products.findIndex(p => p.id === id);
      if(index !== -1) {
        this.products.splice(index, 1);
      }
      this.rest.deleteProduct(id).subscribe()
    }
  }

  onFileSelected(event: any, id: string | undefined) {
    if (id) {
      let formData: FormData = new FormData();
      formData.append("file", event.target.files[0]);
      this.rest.saveProductImg(formData, id).subscribe(() => {
          const reader = new FileReader();
          reader.onloadend = () => {
            const productToUpdate = this.products.find(product => id === product.id);
            if (productToUpdate) {
              productToUpdate.productPhoto = reader.result;
            }
          };
          reader.readAsDataURL(event.target.files[0]);
        }
      );
    }
  }

  deleteImg(id: string | undefined, input: any) {
    if (id) {
      this.rest.deleteProductImg(id).subscribe(() => {
        const productToUpdate = this.products.find(product => id === product.id);
        if (productToUpdate) {
          productToUpdate.productPhoto = "assets/no_photo.jpg";
          input.value = ''
        }
      });
    }
  }

  loadImages() {
    this.rest.getAllImages(this.products[0].retailerID).subscribe(res => {
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

  completeOrder(id: string | undefined) {
    if (id) {
      this.orders = this.orders.filter((order) => order.id !== id);
      this.rest.completeOrder(id).subscribe()
    }
  }
}
interface Image {
  id: string;
  image: any;
}

