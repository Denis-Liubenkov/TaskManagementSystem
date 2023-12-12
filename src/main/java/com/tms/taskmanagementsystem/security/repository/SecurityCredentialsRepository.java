package com.tms.taskmanagementsystem.security.repository;

import com.tms.taskmanagementsystem.security.domain.SecurityCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SecurityCredentialsRepository extends JpaRepository<SecurityCredentials, Integer> {

    Optional<SecurityCredentials> findByEmail(String login);
}
