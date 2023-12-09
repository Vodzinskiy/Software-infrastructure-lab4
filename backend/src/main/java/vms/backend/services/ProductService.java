package vms.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vms.backend.entity.Product;
import vms.backend.exception.ForbiddenException;
import vms.backend.exception.NotFoundException;
import vms.backend.repository.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    private final JwtService jwtService;

    @Autowired
    public ProductService(ProductRepository productRepository, JwtService jwtService) {
        this.productRepository = productRepository;
        this.jwtService = jwtService;
    }

    public Product createProduct(Product product, String jwt) {
        Product newProduct = new Product(UUID.randomUUID(), product.getTitle(), product.getPrice(), jwtService.jwtToUUID(jwt));
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

    public void deleteByID(UUID id, String jwt) {
        Product product = getByID(id);
        if (product.getRetailer() != jwtService.jwtToUUID(jwt)) {
            throw new ForbiddenException("You are not the owner of this product!");
        }
        productRepository.deleteById(id);
    }

    public Product editByID(UUID id, Product update, String jwt) {
        Product product = getByID(id);
        if (product.getRetailer() != jwtService.jwtToUUID(jwt)) {
            throw new ForbiddenException("You are not the owner of this product!");
        }
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

    public List<Product> getAllByRetailer(String jwt) {
        UUID id = jwtService.jwtToUUID(jwt);
        return productRepository.findAllByRetailer(id);
    }
}
