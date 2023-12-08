package vms.backend.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Document(collection = "products")
@Getter
@Setter
public class Product {

    @Id
    private UUID id;

    private String title;

    private Integer price;

    private UUID retailer;

    public Product(UUID  id, String title, Integer price, UUID retailer) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.retailer = retailer;
    }
}