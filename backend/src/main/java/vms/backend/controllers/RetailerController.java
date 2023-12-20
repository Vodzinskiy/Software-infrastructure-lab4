package vms.backend.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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

    @GetMapping
    public ResponseEntity<List<Product>> getAllProduct(@CookieValue("jwtToken") String jwtToken) {
        return ResponseEntity.ok(productService.getAllByRetailer(jwtToken));
    }

    @PostMapping("/signup")
    public ResponseEntity<Retailer> registerRetailer(@RequestBody Retailer retailer) {
        return ResponseEntity.status(HttpStatus.CREATED).body(retailerService.createRetailer(retailer));
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public void login(@RequestBody Retailer retailer, HttpServletResponse response) {
        retailerService.login(retailer, response);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByID(@CookieValue("jwtToken") String jwtToken) {
        retailerService.deleteByID(jwtToken);
    }

    @PatchMapping
    public ResponseEntity<Retailer> editRetailer(@RequestBody Retailer retailer,
                                                 @CookieValue("jwtToken") String jwtToken) {
        return ResponseEntity.ok(retailerService.editRetailer(retailer, jwtToken));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Retailer> getRetailer(@PathVariable UUID id,
                                                @CookieValue("jwtToken") String jwtToken) {
        return ResponseEntity.ok(retailerService.getRetailerByID(id, jwtToken));
    }

    @DeleteMapping("/photo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePhoto(@CookieValue("jwtToken") String jwtToken) {
        retailerService.deletePhoto(jwtToken);
    }

    @PostMapping("/photo")
    @ResponseStatus(HttpStatus.OK)
    public void savePhoto(@CookieValue("jwtToken") String jwtToken,
                          @RequestBody MultipartFile file) {
        retailerService.savePhoto(jwtToken, file);
    }

    @GetMapping(value = "/photo")
    public ResponseEntity<?> getPhoto(@CookieValue("jwtToken") String jwtToken) {
        return ResponseEntity.ok(retailerService.getPhoto(jwtToken));
    }
}
