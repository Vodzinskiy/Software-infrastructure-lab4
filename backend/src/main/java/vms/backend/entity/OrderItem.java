package vms.backend.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Getter
@Setter
public class OrderItem {

    private Integer quantity;

    @DBRef(lazy = true)
    private Product product;

    public OrderItem(Integer quantity, Product product) {
        this.quantity = quantity;
        this.product = product;
    }
}
