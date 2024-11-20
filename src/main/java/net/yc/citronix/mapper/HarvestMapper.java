package net.yc.citronix.mapper;

import net.yc.citronix.DTO.FieldDTO;
import net.yc.citronix.DTO.HarvestDTO;
import net.yc.citronix.model.Field;
import net.yc.citronix.model.Harvest;
import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;

@Mapper(config = BaseMapperConfig.class)
public interface HarvestMapper extends BaseMapper<Harvest, HarvestDTO> {
}
