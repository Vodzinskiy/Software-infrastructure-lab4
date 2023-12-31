package vms.backend.services;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vms.backend.entity.Retailer;
import vms.backend.exception.AlreadyExistsException;
import vms.backend.exception.ForbiddenException;
import vms.backend.exception.NotFoundException;
import vms.backend.repository.ProductRepository;
import vms.backend.repository.RetailerRepository;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@Service
public class RetailerService {

    private final RetailerRepository retailerRepository;
    private final ProductRepository productRepository;
    private final PasswordEncoder encoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;


    @Autowired
    public RetailerService(RetailerRepository retailerRepository, ProductRepository productRepository, PasswordEncoder encoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.retailerRepository = retailerRepository;
        this.productRepository = productRepository;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public Retailer createRetailer(Retailer retailer) {

        if (retailerRepository.findByEmail(retailer.getEmail()).isPresent()) {
            throw new AlreadyExistsException("Retailer with email " + retailer.getEmail() + "already exists");
        }

        Retailer createdRetailer = new Retailer(UUID.randomUUID(),
                retailer.getFullName(),
                retailer.getEmail(),
                encoder.encode(retailer.getPassword()),
                retailer.getBirthDate());
        retailerRepository.save(createdRetailer);

        return createdRetailer;
    }

    public void login(Retailer retailer, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(retailer.getEmail(), retailer.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtService.generateJwtToken(authentication);

        Cookie cookie = new Cookie("jwtToken", jwt);
        cookie.setMaxAge(24 * 60 * 60 * 30);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public Retailer getByID(UUID id) {
        Optional<Retailer> retailer = retailerRepository.findById(id);
        if (retailer.isEmpty()) {
            throw new NotFoundException("Retailer with id " + id + " not found");
        }
        return retailer.get();
    }

    public Retailer getRetailerByID(UUID id, String jwt) {
       /*if (!jwtService.jwtToUUID(jwt).equals(id)) {
           throw new ForbiddenException("");
       }*/
       return getByID(id);
    }



    public void deleteByID(String jwt) {
        UUID id = jwtService.jwtToUUID(jwt);
        getByID(id);
        retailerRepository.deleteById(id);
        productRepository.deleteByRetailerID(id);
    }

    public Retailer editRetailer(Retailer update, String jwt) {
        Retailer retailer = getByID(jwtService.jwtToUUID(jwt));
        if(update.getFullName() != null) {
            retailer.setFullName(update.getFullName());
        }
        if(update.getEmail() != null) {
            retailer.setEmail(update.getEmail());
        }
        if(update.getBirthDate() != null) {
            retailer.setBirthDate(update.getBirthDate());
        }
        if(update.getPassword() != null) {
            retailer.setPassword(encoder.encode(update.getPassword()));
        }
        return retailerRepository.save(retailer);
    }

    public void deletePhoto(String jwt) {
        UUID id = jwtService.jwtToUUID(jwt);
        Retailer retailer = getByID(id);
        retailer.setPhoto(null);
        retailerRepository.save(retailer);
    }

    public void savePhoto(String jwt, MultipartFile file) {
        UUID id = jwtService.jwtToUUID(jwt);
        Retailer retailer = getByID(id);
        try {
            retailer.setPhoto(file.getBytes());
            retailerRepository.save(retailer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] getPhoto(String jwt) {
        UUID id = jwtService.jwtToUUID(jwt);
        Retailer retailer = getByID(id);
        return retailer.getPhoto();
    }
}
