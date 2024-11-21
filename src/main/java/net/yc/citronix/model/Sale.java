package net.yc.citronix.model;

import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "sales")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @NotBlank(message = "Client name is required.")
    @Column(name = "client_name", nullable = false)
    private String clientName;

    @NotNull(message = "Sale date is required.")
    @Column(name = "sale_date", nullable = false)
    private LocalDate saleDate;

    @Positive(message = "Unit price must be a positive number.")
    @Column(name = "unit_price", nullable = false)
    private double unitPrice;

    @Positive(message = "Quantity must be a positive number.")
    @Column(nullable = false)
    private double quantity;

    @NotNull(message = "Harvest ID is required.")
    @Column(name = "harvest_id", nullable = false)
    private Long harvestId; // Reference to the associated harvest

    @Column(nullable = false)
    private double revenue; // Automatically calculated: revenue = quantity * unitPrice
}
