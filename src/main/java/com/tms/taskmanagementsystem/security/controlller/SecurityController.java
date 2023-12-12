package com.tms.taskmanagementsystem.security.controlller;

import com.tms.taskmanagementsystem.security.domain.AuthRequest;
import com.tms.taskmanagementsystem.security.domain.AuthResponse;
import com.tms.taskmanagementsystem.security.domain.RegistrationDTO;
import com.tms.taskmanagementsystem.security.service.SecurityService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {
    private static final Logger log = LoggerFactory.getLogger(SecurityController.class);

    private final SecurityService securityService;

    public SecurityController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registration(@Valid @RequestBody RegistrationDTO registrationDTO) {
        securityService.registration(registrationDTO);
        log.info("User " + registrationDTO.getFirstName() + " is successfully registered!");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/authentication")
    public ResponseEntity<AuthResponse> generateToken(@RequestBody AuthRequest authRequest) {
        String token = securityService.generateToken(authRequest);
        if (token.isBlank()) {
            log.info("User " + authRequest.getEmail() + " is authenticated unsuccessfully!");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        log.info("User " + authRequest.getEmail() + " is successfully authenticated!");
        return new ResponseEntity<>(new AuthResponse(token), HttpStatus.OK);
    }
}
