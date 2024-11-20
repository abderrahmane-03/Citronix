package net.yc.citronix.model;

import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "sales")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sale {

    @Id
    private String id;

    @NotBlank(message = "Client name is required.")
    private String clientName;

    @NotNull(message = "Sale date is required.")
    private LocalDate saleDate;

    @Positive(message = "Unit price must be a positive number.")
    private double unitPrice;

    @Positive(message = "Quantity must be a positive number.")
    private double quantity;

    @NotBlank(message = "Harvest ID is required.")
    private String harvestId; // Reference to the associated harvest

    private double revenue; // Automatically calculated: revenue = quantity * unitPrice
}
