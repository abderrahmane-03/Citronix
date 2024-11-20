package net.yc.citronix.model;

import lombok.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
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
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id = UUID.randomUUID();


    @NotBlank(message = "Farm name is required.")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Farm location is required.")
    @Column(nullable = false)
    private String location;

    @Positive(message = "Farm size must be a positive number.")
    @Min(value = 1, message = "Minimum farm size is 1 hectare.")
    @Column(nullable = false)
    private double size;

    @PastOrPresent(message = "Creation date cannot be in the future.")
    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;

}
