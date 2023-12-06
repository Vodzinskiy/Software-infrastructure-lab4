package vms.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vms.backend.entity.Retailer;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RetailerRepository extends JpaRepository<Retailer, UUID> {
    Optional<Retailer> findByFullName(String fullName);
}

