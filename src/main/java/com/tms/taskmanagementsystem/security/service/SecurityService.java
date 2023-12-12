package com.tms.taskmanagementsystem.security.service;

import com.tms.taskmanagementsystem.security.repository.SecurityCredentialsRepository;
import com.tms.taskmanagementsystem.security.JwtUtils;
import com.tms.taskmanagementsystem.security.domain.AuthRequest;
import com.tms.taskmanagementsystem.security.domain.RegistrationDTO;
import com.tms.taskmanagementsystem.security.domain.SecurityCredentials;
import com.tms.taskmanagementsystem.domain.Role;

import com.tms.taskmanagementsystem.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.tms.taskmanagementsystem.repository.UserRepository;

import java.util.Optional;

@Service
@Component
public class SecurityService {
    private static final Logger log = LoggerFactory.getLogger(SecurityService.class);

    private final JwtUtils jwtUtils;

    private final PasswordEncoder passwordEncoder;

    private final User user;

    private final SecurityCredentials securityCredentials;

    private final UserRepository userRepository;

    private final SecurityCredentialsRepository securityCredentialsRepository;

    public SecurityService(JwtUtils jwtUtils, PasswordEncoder passwordEncoder, User user, SecurityCredentials securityCredentials, UserRepository userRepository, SecurityCredentialsRepository securityCredentialsRepository) {
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
        this.user = user;
        this.securityCredentials = securityCredentials;
        this.userRepository = userRepository;
        this.securityCredentialsRepository = securityCredentialsRepository;
    }

    public String generateToken(AuthRequest authRequest) {
        Optional<SecurityCredentials> credentials = securityCredentialsRepository.findByEmail(authRequest.getEmail());
        if (credentials.isPresent() && passwordEncoder.matches(authRequest.getPassword(), credentials.get().getUserPassword())) {
            log.info("Token is successfully generated!");
            return jwtUtils.generateJwtToken(authRequest.getEmail());
        }
        log.info("Token is not generated!");
        return "";
    }

    @Transactional(rollbackFor = Exception.class)
    public void registration(RegistrationDTO registrationDTO) {
        user.setFirstName(registrationDTO.getFirstName());
        user.setLastName(registrationDTO.getLastName());
        user.setEmail(registrationDTO.getEmail());
        User userResult = userRepository.save(user);

        securityCredentials.setEmail(registrationDTO.getEmail());
        securityCredentials.setUserPassword(passwordEncoder.encode(registrationDTO.getUserPassword()));
        securityCredentials.setUserRole(Role.USER);
        securityCredentials.setUserId(userResult.getId());

        userRepository.save(user);
        securityCredentialsRepository.save(securityCredentials);
    }
}
