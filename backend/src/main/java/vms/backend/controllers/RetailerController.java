package vms.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vms.backend.entity.Product;
import vms.backend.entity.Retailer;
import vms.backend.services.ProductService;
import vms.backend.services.RetailerService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/retailer")
public class RetailerController {
    private final ProductService productService;
    private final RetailerService retailerService;

    @Autowired
    public RetailerController(ProductService productService, RetailerService retailerService) {
        this.productService = productService;
        this.retailerService = retailerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Product>> getAllProduct(@PathVariable UUID id) {
        return ResponseEntity.ok(productService.getAllByRetailer(id));
    }

    @PostMapping("/signup")
    public ResponseEntity<Retailer> registerRetailer(@RequestBody Retailer retailer) {
        return ResponseEntity.status(HttpStatus.CREATED).body(retailerService.createRetailer(retailer));
    }
}
