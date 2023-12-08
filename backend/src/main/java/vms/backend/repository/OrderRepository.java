package vms.backend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vms.backend.entity.Order;

import java.util.UUID;

@Repository
public interface OrderRepository extends MongoRepository<Order, UUID> {

}
