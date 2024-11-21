package net.yc.citronix.model;

import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "fields")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Field {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @Positive(message = "Field size must be a positive number.")
    @DecimalMin(value = "0.1", message = "Minimum field size is 0.1 hectare.")
    @Column(nullable = false)
    private double size;

    @NotNull(message = "Farm ID is required.")
    @Column(name = "farm_id", nullable = false)
    private Long farmId; // Reference to the farm

    @PositiveOrZero(message = "Tree count must be zero or a positive number.")
    @Column(name = "tree_count", nullable = false)
    private int treeCount; // Number of trees in this field

    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL)
    private List<Harvest> harvests;
}
