package net.yc.citronix.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import net.yc.citronix.enums.Season;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "harvests", uniqueConstraints = @UniqueConstraint(columnNames = {"field_id", "season"}))
public class Harvest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @NotNull(message = "Season is required.")
    @Column(nullable = false)
    private Season season;

    @JsonFormat(pattern = "yyyy-M-d")
    @NotNull(message = "Harvest date is required.")
    @Column(name = "harvest_date", nullable = false)
    private LocalDate harvestDate;

    @Positive(message = "Total quantity must be a positive number.")
    @Column(name = "total_quantity", nullable = false)
    private double totalQuantity;

    @ManyToOne
    @JoinColumn(name = "field_id", nullable = false)
    private Field field;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "harvest", fetch = FetchType.LAZY)
    private List<HarvestDetail> harvestDetails;
}