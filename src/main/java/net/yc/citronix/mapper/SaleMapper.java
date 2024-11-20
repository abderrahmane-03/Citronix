package net.yc.citronix.mapper;

import net.yc.citronix.DTO.SaleDTO;
import net.yc.citronix.model.Sale;
import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;

@Mapper(componentModel = "spring")
public interface SaleMapper extends BaseMapper<Sale, SaleDTO> {
}
