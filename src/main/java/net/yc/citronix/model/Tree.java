package net.yc.citronix.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Document(collection = "trees")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tree {

    @Id
    private String id;

    @NotNull(message = "Plantation date is required.")
    private LocalDate plantationDate;

    @Positive(message = "Tree age must be a positive number.")
    private int age; // Automatically calculated based on plantation date

    @NotBlank(message = "Field ID is required.")
    private String fieldId; // Reference to the field where the tree is located

    private boolean productive; // Indicates if the tree is still productive
}
