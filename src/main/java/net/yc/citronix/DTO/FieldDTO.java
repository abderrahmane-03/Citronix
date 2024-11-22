package net.yc.citronix.DTO;
import jakarta.validation.constraints.*;
import lombok.Data;


@Data
public class FieldDTO {

    private Long id;

    @Positive(message = "Field size must be a positive number.")
    @DecimalMin(value = "0.1", message = "Minimum field size is 0.1 hectare.")
    private double size;

    @PositiveOrZero(message = "Tree count must be zero or a positive number.")
    private int treeCount;

    @NotNull(message = "Farm ID is required.")
    private Long farmId;


}
