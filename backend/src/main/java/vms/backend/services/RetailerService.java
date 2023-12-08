package vms.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vms.backend.entity.Retailer;
import vms.backend.repository.RetailerRepository;

import java.util.UUID;

@Service
public class RetailerService {

    private final RetailerRepository retailerRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public RetailerService(RetailerRepository retailerRepository, PasswordEncoder encoder) {
        this.retailerRepository = retailerRepository;
        this.encoder = encoder;
    }

    public Retailer createRetailer(Retailer retailer) {
        Retailer createdRetailer = new Retailer(UUID.randomUUID(),
                retailer.getFullName(),
                retailer.getEmail(),
                encoder.encode(retailer.getPassword()),
                retailer.getSex(),
                retailer.getBirthDate());
        retailerRepository.save(createdRetailer);
        return createdRetailer;
    }
}
