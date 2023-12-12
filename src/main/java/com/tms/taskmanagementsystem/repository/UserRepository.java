package com.tms.taskmanagementsystem.repository;

import com.tms.taskmanagementsystem.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @NonNull
    Optional<User> findById(@NonNull Integer id);


    void deleteById(@NonNull Integer id);
}
