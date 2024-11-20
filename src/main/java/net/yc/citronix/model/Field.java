package net.yc.citronix.model;

import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.UUID;

@Entity
@Table(name = "fields")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Field {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id = UUID.randomUUID();

    @Positive(message = "Field size must be a positive number.")
    @Min(value = 1, message = "Minimum field size is 0.1 hectare.")
    @Max(value = 50, message = "Field size cannot exceed 50% of the farm's total size.")
    @Column(nullable = false)
    private double size; // Field size in hectares

    @NotBlank(message = "Farm ID is required.")
    @Column(name = "farm_id", nullable = false)
    private String farmId; // Reference to the farm

    @PositiveOrZero(message = "Tree count must be zero or a positive number.")
    @Column(name = "tree_count", nullable = false)
    private int treeCount; // Number of trees in this field
}
