package net.yc.citronix.model;

import lombok.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;

@Entity
@Table(name = "farms")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Farm {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @NotBlank(message = "Farm name is required.")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Farm location is required.")
    @Column(nullable = false)
    private String location;

    @Positive(message = "Farm size must be a positive number.")
    @DecimalMin(value = "0.2", message = "Minimum farm size is 0.2 hectare.")
    @Column(nullable = false)
    private double size;

    @PastOrPresent(message = "Creation date cannot be in the future.")
    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "farm", fetch = FetchType.LAZY)
    private List<Field> fields;

}
