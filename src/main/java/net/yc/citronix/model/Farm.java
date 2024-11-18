package net.yc.citronix.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Document(collection = "farms")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Farm {

    @Id
    private String id;

    @NotBlank(message = "Farm name is required.")
    private String name;

    @NotBlank(message = "Farm location is required.")
    private String location;

    @Positive(message = "Farm size must be a positive number.")
    @Min(value = 1, message = "Minimum farm size is 1 hectare.")
    private double size; // Farm area in hectares

    @PastOrPresent(message = "Creation date cannot be in the future.")
    private LocalDate creationDate;

    private List<Field> fields; // Embedded fields in the farm
}
