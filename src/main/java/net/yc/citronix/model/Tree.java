package net.yc.citronix.model;

import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "trees")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tree {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id = UUID.randomUUID();

    @NotNull(message = "Plantation date is required.")
    @Column(name = "plantation_date", nullable = false)
    private LocalDate plantationDate;

    @Positive(message = "Tree age must be a positive number.")
    @Column(nullable = false)
    private int age; // Automatically calculated based on plantation date

    @NotBlank(message = "Field ID is required.")
    @Column(name = "field_id", nullable = false)
    private String fieldId; // Reference to the field where the tree is located

    @Column(nullable = false)
    private boolean productive; // Indicates if the tree is still productive
}
