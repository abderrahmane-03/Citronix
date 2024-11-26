package net.yc.citronix.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SaleDTO {


    private Long id;
    @NotBlank(message = "Client name is required.")
    private String clientName;
    @NotNull(message = "Sale date is required.")
    private LocalDate saleDate;
    @Positive(message = "Unit price must be a positive number.")
    private double unitPrice;
    private double quantity;
    @NotNull(message = "Harvest ID is required.")
    private Long harvestId;
    private double revenue;
}