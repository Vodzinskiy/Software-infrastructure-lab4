package vms.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vms.backend.entity.Order;
import vms.backend.exception.NotFoundException;
import vms.backend.repository.OrderRepository;
import vms.backend.repository.ProductRepository;

import java.util.UUID;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public Order createOrder(Order order) {
        order.getProducts().forEach(product -> {
            if (!productRepository.existsById(product.getId())) {
                throw new NotFoundException("Product with id " + product.getId() + " not found");
            }
        });

        Order newOrder = new Order(UUID.randomUUID(),
                order.getFullName(),
                order.getBirthDate(),
                order.getAddress(),
                order.getProducts());
        orderRepository.save(newOrder);
        return newOrder;
    }


}
