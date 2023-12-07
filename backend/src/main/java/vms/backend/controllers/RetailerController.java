package vms.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vms.backend.entity.Product;
import vms.backend.services.ProductService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/retailer")
public class RetailerController {
    private final ProductService productService;

    @Autowired
    public RetailerController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Product>> getAllProduct(@PathVariable UUID id) {
        return ResponseEntity.ok(productService.getAllByRetailer(id));
    }
}
