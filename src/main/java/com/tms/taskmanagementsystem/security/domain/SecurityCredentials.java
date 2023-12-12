package com.tms.taskmanagementsystem.security.domain;

import com.tms.taskmanagementsystem.domain.Role;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Entity(name = "security_credentials")
@Component
public class SecurityCredentials {
    @Id
    @SequenceGenerator(name = "securityCredentialsSeq", sequenceName = "security_credentials_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "securityCredentialsSeq")
    private Integer id;

    @Column(name = "email")
    private String email;

    @Column(name = "user_password")
    private String userPassword;

    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    private Role userRole;

    @Column(name = "user_id")
    private Integer userId;
}
