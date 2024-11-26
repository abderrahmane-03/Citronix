package net.yc.citronix.DTO;
import lombok.Data;
import net.yc.citronix.enums.Season;
import java.time.LocalDate;
import java.util.List;

@Data
public class HarvestDTO {
    private Long id;
    private Season season;
    private LocalDate harvestDate;
    private double totalQuantity;
    private FieldDTO field;
    private List<HarvestDetailDTO> harvestDetails;

}