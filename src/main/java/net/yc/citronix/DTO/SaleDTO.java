package net.yc.citronix.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

@Data
public class SaleDTO {


    private String id;
    private String clientName;
    private LocalDate saleDate;
    private double unitPrice;
    private double quantity;
    private String harvestId;
    private double revenue;
}
