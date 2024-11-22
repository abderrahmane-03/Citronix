package net.yc.citronix.mapper;

import net.yc.citronix.DTO.HarvestDetailDTO;
import net.yc.citronix.model.HarvestDetail;
import org.mapstruct.Mapper;

@Mapper(config = BaseMapper.class)
public interface HarvestDetailMapper extends BaseMapper<HarvestDetail, HarvestDetailDTO> {



}
