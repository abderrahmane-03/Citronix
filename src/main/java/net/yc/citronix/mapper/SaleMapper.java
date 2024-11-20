package net.yc.citronix.mapper;

import net.yc.citronix.DTO.SaleDTO;
import net.yc.citronix.model.Sale;
import org.mapstruct.Mapper;

@Mapper(config = BaseMapperConfig.class)
public interface SaleMapper extends BaseMapper<Sale, SaleDTO> {
}
