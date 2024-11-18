package net.yc.citronix.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.*;

@Document(collection = "fields")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Field {

    @Id
    private String id;

    @Positive(message = "Field size must be a positive number.")
    @Min(value = 1, message = "Minimum field size is 0.1 hectare.")
    @Max(value = 50, message = "Field size cannot exceed 50% of the farm's total size.")
    private double size; // Field size in hectares

    private String farmId; // Reference to the farm

    @PositiveOrZero(message = "Tree count must be zero or a positive number.")
    private int treeCount; // Number of trees in this field
}
