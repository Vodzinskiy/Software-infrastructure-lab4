package vms.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vms.backend.entity.Order;
import vms.backend.services.OrderService;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(order));
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrdersForRetailer(@CookieValue("jwtToken") String jwtToken) {
        return ResponseEntity.ok(orderService.getAllOrdersForRetailer(jwtToken));
    }

}
