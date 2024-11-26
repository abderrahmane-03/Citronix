package net.yc.citronix.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @PositiveOrZero(message = "Tree count must be zero or a positive number.")
    @Column(name = "tree_count", nullable = false)
    private int treeCount;


    @ManyToOne
    @NotNull(message = "Farm ID is required.")
    @JoinColumn(name = "farm_id", nullable = false)
    private Farm farm;

    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL)
    private List<Tree> trees;

    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL)
    private List<Harvest> harvests;
}
