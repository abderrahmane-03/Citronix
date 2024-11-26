package net.yc.citronix.DTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class HarvestDetailDTO {
    private Long id;
    private Long treeId;
    private double quantity;
}