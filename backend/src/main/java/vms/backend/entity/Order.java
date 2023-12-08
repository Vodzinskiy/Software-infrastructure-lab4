package vms.backend.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Document(collection = "orders")
@Getter
@Setter
public class Order {

    @Id
    private UUID id;

    private String fullName;

    private LocalDate birthDate;

    private String address;

    @DBRef(lazy = true)
    private List<Product> products;

    public Order(UUID id, String fullName, LocalDate birthDate, String address, List<Product> products) {
        this.id = id;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.address = address;
        this.products = products;
    }
}
