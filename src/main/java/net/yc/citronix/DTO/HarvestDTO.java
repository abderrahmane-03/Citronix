package net.yc.citronix.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import net.yc.citronix.enums.Season;
import net.yc.citronix.model.HarvestDetail;

import java.time.LocalDate;
import java.util.List;

@Data
public class HarvestDTO {
    private Long id;
    private Season season;
    private LocalDate harvestDate;
    private double totalQuantity;
    private Long fieldId;
    private List<HarvestDetail> harvestDetails;

}
