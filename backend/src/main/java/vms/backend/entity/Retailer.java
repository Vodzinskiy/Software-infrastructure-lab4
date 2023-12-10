package vms.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;


import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "retailers")
@Getter
@Setter
public class Retailer {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDate birthDate;

    public Retailer(UUID id, String fullName, String email, String password, LocalDate birthDate) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.birthDate = birthDate;
    }

    public Retailer() {
    }
}
