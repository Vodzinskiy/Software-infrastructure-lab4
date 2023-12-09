package vms.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vms.backend.entity.Order;
import vms.backend.exception.NotFoundException;
import vms.backend.repository.OrderRepository;
import vms.backend.repository.ProductRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    private final JwtService jwtService;

    @Autowired
    public OrderService(OrderRepository orderRepository, ProductRepository productRepository, JwtService jwtService) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.jwtService = jwtService;
    }

    public Order createOrder(Order order) {
        order.getProducts().forEach(product -> {
            if (!productRepository.existsById(product.getProduct().getId())) {
                throw new NotFoundException("Product with id " + product.getProduct().getId() + " not found");
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

    public List<Order> getAllOrdersForRetailer(String jwtToken) {
        UUID id = jwtService.jwtToUUID(jwtToken);
        return orderRepository.findAll().stream()
                .filter(order ->
                        order.getProducts().stream()
                                .anyMatch(product ->
                                        id.equals(product.getProduct().getRetailerID()))
                )
                .peek(order -> order.setProducts(
                        order.getProducts().stream()
                                .filter(product ->
                                        id.equals(product.getProduct().getRetailerID()))
                                .collect(Collectors.toList())))
                .collect(Collectors.toList());
    }
}
