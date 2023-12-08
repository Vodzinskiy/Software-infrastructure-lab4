package vms.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vms.backend.entity.Retailer;
import vms.backend.repository.RetailerRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final RetailerRepository retailerRepository;
    @Autowired
    public UserDetailsServiceImpl(RetailerRepository retailerRepository) {
        this.retailerRepository = retailerRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Retailer retailer = retailerRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));

        return UserDetailsImpl.build(retailer);
    }

}
