package vms.backend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import vms.backend.entity.Order;

import java.util.UUID;

public interface OrderRepository extends MongoRepository<Order, UUID> {

}
