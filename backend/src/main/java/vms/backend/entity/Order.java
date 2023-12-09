package vms.backend.entity;

import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
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

    @Embedded
    private List<OrderItem> products;

    public Order(UUID id, String fullName, LocalDate birthDate, String address, List<OrderItem> products) {
        this.id = id;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.address = address;
        this.products = products;
    }
}
