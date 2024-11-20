package net.yc.citronix.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import net.yc.citronix.model.HarvestDetail;

import java.time.LocalDate;
import java.util.List;

@Data
public class HarvestDTO {
    private String id;
    private String season;
    private LocalDate harvestDate;
    private double totalQuantity;
    private String fieldId;
    private List<HarvestDetail> harvestDetails;

}
