package net.yc.citronix.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.*;

@Document(collection = "harvestDetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HarvestDetail {

    @Id
    private String id;

    @NotBlank(message = "Tree ID is required.")
    private String treeId; // Reference to the specific tree

    @Positive(message = "Quantity must be a positive number.")
    private double quantity; // Quantity harvested from the tree in kilograms
}
