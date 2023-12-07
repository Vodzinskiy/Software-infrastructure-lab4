package vms.backend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import vms.backend.entity.Product;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends MongoRepository<Product, UUID> {
    List<Product> findAllByRetailer(UUID retailerId);
}
