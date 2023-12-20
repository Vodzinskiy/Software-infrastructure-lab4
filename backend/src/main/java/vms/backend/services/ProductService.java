package vms.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vms.backend.entity.Product;
import vms.backend.exception.ForbiddenException;
import vms.backend.exception.NotFoundException;
import vms.backend.payload.response.ImagesResponse;
import vms.backend.repository.ProductRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    private final JwtService jwtService;

    @Value("${volume.path}")
    private String path;

    @Autowired
    public ProductService(ProductRepository productRepository, JwtService jwtService) {
        this.productRepository = productRepository;
        this.jwtService = jwtService;
    }

    public Product createProduct(Product product, String jwt) {
        Product newProduct = new Product(UUID.randomUUID(),
                product.getTitle(),
                product.getPrice(),
                jwtService.jwtToUUID(jwt),
                jwtService.getNameFromJwtToken(jwt));
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
        if (!product.getRetailerID().equals(jwtService.jwtToUUID(jwt))) {
            throw new ForbiddenException("You are not the owner of this product!");
        }
        productRepository.deleteById(id);
    }

    public Product editByID(UUID id, Product update, String jwt) {
        Product product = getByID(id);
        if (!product.getRetailerID().equals(jwtService.jwtToUUID(jwt))) {
            throw new ForbiddenException("You are not the owner of this product!");
        }
        if (update.getTitle() != null) {
            product.setTitle(update.getTitle());
        }
        if (update.getPrice() != null) {
            product.setPrice(update.getPrice());
        }
        /*if(update.getRetailer() != null) {
            product.setRetailer(update.getRetailer());
        }*/
        return productRepository.save(product);
    }

    public List<Product> getAllByRetailer(String jwt) {
        UUID id = jwtService.jwtToUUID(jwt);
        return productRepository.findAllByRetailerID(id);
    }

    public void saveImage(String jwt, UUID id, MultipartFile file) {
        Product product = getByID(id);
        if (!product.getRetailerID().equals(jwtService.jwtToUUID(jwt))) {
            throw new ForbiddenException("You are not the owner of this product!");
        }
        String originalFilename = file.getOriginalFilename();
        assert originalFilename != null;
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFilename = id + extension;
        String filePath = path + newFilename;
        try {
            file.transferTo(new File(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteImage(String jwt, UUID id) {
        Product product = getByID(id);
        if (!product.getRetailerID().equals(jwtService.jwtToUUID(jwt))) {
            throw new ForbiddenException("You are not the owner of this product!");
        }
        File directory = new File(path);
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.getName().startsWith(id.toString() + ".")) {
                    file.delete();
                    break;
                }
            }
        }
    }

    public List<ImagesResponse> getAllImages(String id) {
        List<Product> products = null;
        if (id != null && !id.isEmpty()) {
            products = productRepository.findAllByRetailerID(UUID.fromString(id));
        }

        File directory = new File(path);
        List<ImagesResponse> images = new ArrayList<>();
        for (File f : Objects.requireNonNull(directory.listFiles())) {
            int last = f.getName().lastIndexOf(".");
            boolean productExists = true;
            if (id != null && !id.isEmpty()) {
                productExists = products.stream()
                        .anyMatch(p -> p.getId().toString().equals(f.getName().substring(0, last)));
            }
            if (productExists) {
                try {
                    images.add(new ImagesResponse(f.getName().substring(0, last),
                            Files.readAllBytes(Paths.get(f.toURI()))));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return images;
    }
}
