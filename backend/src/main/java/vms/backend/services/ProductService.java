package vms.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vms.backend.entity.Product;
import vms.backend.exception.NotFoundException;
import vms.backend.repository.ProductRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(String title, int price, UUID retailer) {
        Product product = new Product(UUID.randomUUID(), title, price, retailer);
        productRepository.save(product);
        return product;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getByID(UUID uuid) {
        Optional<Product> product = productRepository.findById(uuid);
        if (product.isEmpty()) {
            throw new NotFoundException("Product with id " + uuid + " not found");
        }
        return product.get();
    }

}
