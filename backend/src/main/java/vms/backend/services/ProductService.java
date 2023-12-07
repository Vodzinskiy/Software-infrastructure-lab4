package vms.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vms.backend.entity.Product;
import vms.backend.exception.NotFoundException;
import vms.backend.repository.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(Product product) {
        Product newProduct = new Product(UUID.randomUUID(), product.getTitle(), product.getPrice(), product.getRetailer());
        productRepository.save(newProduct);
        return newProduct;
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

    public void deleteByID(UUID id) {
        getByID(id);
        productRepository.deleteById(id);
    }

    public Product editByID(UUID id, Product update) {
        Product product = getByID(id);
        if(update.getTitle() != null) {
            product.setTitle(update.getTitle());
        }
        if(update.getPrice() != null) {
            product.setPrice(update.getPrice());
        }
        if(update.getRetailer() != null) {
            product.setRetailer(update.getRetailer());
        }
        return productRepository.save(product);
    }
}
