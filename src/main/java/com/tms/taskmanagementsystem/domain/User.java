package com.tms.taskmanagementsystem.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@Schema(description = "description of user")
@Component
@Entity(name = "users")
public class User {
    @Schema(description = "it is unique user`s id")
    @Id
    @SequenceGenerator(name = "users_seq", sequenceName = "users_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq")
    private Integer id;

    @Pattern(regexp = "^[А-Яа-яЁё]+$")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Pattern(regexp = "^[А-Яа-яЁё]+$")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Email
    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Task> taskList = new ArrayList<>();
}

