package net.yc.citronix.mapper;

import net.yc.citronix.DTO.HarvestDTO;
import net.yc.citronix.model.Harvest;
import org.mapstruct.Mapper;

@Mapper(config = BaseMapper.class)
public interface HarvestMapper extends BaseMapper<Harvest, HarvestDTO> {
}
