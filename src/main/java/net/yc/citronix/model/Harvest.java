package net.yc.citronix.model;

import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "harvests")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Harvest {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id = UUID.randomUUID();

    @NotBlank(message = "Season is required.")
    @Column(nullable = false)
    private String season; // E.g., "Spring", "Summer", "Autumn", "Winter"

    @NotNull(message = "Harvest date is required.")
    @Column(name = "harvest_date", nullable = false)
    private LocalDate harvestDate;

    @Positive(message = "Total quantity must be a positive number.")
    @Column(name = "total_quantity", nullable = false)
    private double totalQuantity; // Total quantity harvested in kilograms

    @NotBlank(message = "Field ID is required.")
    @Column(name = "field_id", nullable = false)
    private String fieldId; // Reference to the field being harvested

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "harvest", fetch = FetchType.LAZY)
    private List<HarvestDetail> harvestDetails; // Details of the harvest by tree
}
