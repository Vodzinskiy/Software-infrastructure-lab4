package vms.backend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vms.backend.entity.Product;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends MongoRepository<Product, UUID> {
    List<Product> findAllByRetailerID(UUID retailerId);
}
