package domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.stereotype.Component;

@Entity(name = "executor")
@Data
@Schema(description = "description of executor")
@Component
public class Executor {
    @Schema(description = "it is unique executor`s id")
    @SequenceGenerator(name = "executor_seq", sequenceName = "executor_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "executor_seq")
    @Id
    private Integer id;

    @Pattern(regexp = "^[А-Яа-яЁё]+$")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Pattern(regexp = "^[А-Яа-яЁё]+$")
    @Column(name = "last_name", nullable = false)
    private String lastName;
}
