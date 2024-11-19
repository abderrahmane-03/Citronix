package net.yc.citronix.mapper;

import net.yc.citronix.DTO.FarmDTO;
import net.yc.citronix.model.Farm;
import org.mapstruct.MapperConfig;

@MapperConfig(componentModel = "spring")
public interface FarmMapper extends BaseMapper<Farm, FarmDTO> {
}
