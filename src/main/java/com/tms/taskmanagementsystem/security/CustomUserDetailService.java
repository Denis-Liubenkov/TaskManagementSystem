package com.tms.taskmanagementsystem.security;

import com.tms.taskmanagementsystem.security.domain.SecurityCredentials;
import com.tms.taskmanagementsystem.security.repository.SecurityCredentialsRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final SecurityCredentialsRepository securityCredentialsRepository;

    public CustomUserDetailService(SecurityCredentialsRepository securityCredentialsRepository) {
        this.securityCredentialsRepository = securityCredentialsRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<SecurityCredentials> securityCredentials = securityCredentialsRepository.findByEmail(username);
        if (securityCredentials.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        return User
                .withUsername(securityCredentials.get().getEmail())
                .password(securityCredentials.get().getUserPassword())
                .roles(securityCredentials.get().getUserRole().toString())
                .build();
    }
}
