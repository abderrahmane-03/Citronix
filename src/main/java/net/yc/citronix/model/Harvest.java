package net.yc.citronix.model;

import lombok.*;
import net.yc.citronix.model.HarvestDetail;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Document(collection = "harvests")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Harvest {

    @Id
    private String id;

    @NotBlank(message = "Season is required.")
    private String season; // E.g., "Spring", "Summer", "Autumn", "Winter"

    @NotNull(message = "Harvest date is required.")
    private LocalDate harvestDate;

    @Positive(message = "Total quantity must be a positive number.")
    private double totalQuantity; // Total quantity harvested in kilograms

    @NotBlank(message = "Field ID is required.")
    private String fieldId; // Reference to the field being harvested

    private List<HarvestDetail> harvestDetails; // Details of the harvest by tree
}
