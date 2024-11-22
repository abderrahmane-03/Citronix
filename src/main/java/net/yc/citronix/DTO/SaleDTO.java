package net.yc.citronix.DTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SaleDTO {


    private Long id;
    private String clientName;
    private LocalDate saleDate;
    private double unitPrice;
    private double quantity;
    private Long harvestId;
    private double revenue;
}
