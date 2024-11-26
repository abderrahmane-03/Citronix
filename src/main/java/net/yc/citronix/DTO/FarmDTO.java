package net.yc.citronix.DTO;
import jakarta.persistence.Column;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.time.LocalDate;
@Data
public class FarmDTO {

    private Long id;

    @NotBlank(message = "Farm name is required.")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Farm location is required.")
    @Column(nullable = false)
    private String location;

    @Positive(message = "Farm size must be a positive number.")
    @DecimalMin(value = "0.2", message = "Minimum farm size is 0.2 hectare.")
    @Column(nullable = false)
    private double size;

    @PastOrPresent(message = "Creation date cannot be in the future.")
    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;

}
