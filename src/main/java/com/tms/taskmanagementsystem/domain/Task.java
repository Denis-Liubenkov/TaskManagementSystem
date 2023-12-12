package com.tms.taskmanagementsystem.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.stereotype.Component;

@Entity(name = "task")
@Data
@Schema(description = "description of task")
@Component
public class Task {
    @Schema(description = "it is unique task`s id")
    @Id
    @SequenceGenerator(name = "task_seq", sequenceName = "task_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_seq")
    private Integer id;

    @Pattern(regexp = "^[А-Яа-яЁё]+$")
    @Column(name = "title", nullable = false)
    private String title;

    @Pattern(regexp = "^[А-Яа-яЁё]+$")
    @Column(name = "description", nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false)
    private Priority priority;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
