import {NgModule} from "@angular/core";
import {ProductRepository} from "./product.repository";
import {StaticDataSource} from "./static.datasource";
import {Cart} from "./cart.model";
import {Order} from "./order.model";
import {OrderRepository} from "./order.repository";
import {RestDataSource} from "./rest.datasource";
import {HttpClientModule} from "@angular/common/http";
import {SortService} from "./sort.service";

@NgModule({
  imports: [HttpClientModule],
  providers: [ProductRepository, StaticDataSource, Cart, Order, OrderRepository,
    {provide: StaticDataSource, useClass: RestDataSource}, SortService]
})
export class ModelModule {
}
