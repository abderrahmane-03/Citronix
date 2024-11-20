package net.yc.citronix.model;

import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.UUID;

@Entity
@Table(name = "harvest_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HarvestDetail {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id = UUID.randomUUID();

    @NotBlank(message = "Tree ID is required.")
    @Column(name = "tree_id", nullable = false)
    private String treeId;

    @Positive(message = "Quantity must be a positive number.")
    @Column(nullable = false)
    private double quantity;

    @ManyToOne
    @JoinColumn(name = "harvest_id", nullable = false)
    private Harvest harvest;
}
