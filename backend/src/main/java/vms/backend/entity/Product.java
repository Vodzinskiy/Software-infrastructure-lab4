package vms.backend.entity;

import jakarta.persistence.GeneratedValue;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Document(collection = "products")
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    private String title;

    private int price;

    public Product() {
    }

    public Product(UUID id, String title, int price) {
        this.id = id;
        this.title = title;
        this.price = price;
    }

}
