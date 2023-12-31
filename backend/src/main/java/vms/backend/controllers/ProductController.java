package vms.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vms.backend.entity.Product;
import vms.backend.payload.response.ImagesResponse;
import vms.backend.services.ProductService;

import java.io.File;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product,
                                                 @CookieValue("jwtToken") String jwtToken) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(product, jwtToken));
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProduct() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getByID(@PathVariable UUID id) {
        return ResponseEntity.ok(productService.getByID(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByID(@PathVariable UUID id,
                           @CookieValue("jwtToken") String jwtToken) {
        productService.deleteByID(id, jwtToken);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Product> editByID(@PathVariable UUID id,
                                            @RequestBody Product product,
                                            @CookieValue("jwtToken") String jwtToken) {
        return ResponseEntity.ok(productService.editByID(id, product, jwtToken));
    }

    @PostMapping("/img/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void saveImage(@PathVariable UUID id,
                          @RequestBody MultipartFile file,
                          @CookieValue("jwtToken") String jwtToken) {
       productService.saveImage(jwtToken, id, file);
    }

    @DeleteMapping("/img/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteImage(@PathVariable UUID id,
                            @CookieValue("jwtToken") String jwtToken) {
        productService.deleteImage(jwtToken, id);
    }

    @GetMapping("/img")
    public ResponseEntity<List<ImagesResponse>> getAllImages(@RequestParam("id") String id) {
        return ResponseEntity.ok(productService.getAllImages(id));
    }


}