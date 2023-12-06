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

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String sex;

    @Column(columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDate birthDate;

    public Retailer(UUID id, String fullName, String email, String password, String sex, LocalDate birthDate) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.sex = sex;
        this.birthDate = birthDate;
    }

    public Retailer() {

    }
}
