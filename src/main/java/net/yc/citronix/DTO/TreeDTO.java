package net.yc.citronix.DTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.time.LocalDate;

@Data
public class TreeDTO {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @JsonFormat(pattern = "yyyy-M-d")
    @PastOrPresent(message = "Creation date cannot be in the future.")
    @NotNull(message = "Plantation date is required.")
    @Column(name = "plantation_date", nullable = false)
    private LocalDate plantationDate;

    @Column(nullable = false)
    private int age; // Automatically calculated based on plantation date

    @NotNull(message = "Field ID is required.")
    @Column(name = "field_id", nullable = false)
    private Long fieldId; // Reference to the field where the tree is located

    @Transient
    private boolean productive;
}
