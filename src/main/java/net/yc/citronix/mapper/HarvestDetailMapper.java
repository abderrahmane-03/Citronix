package net.yc.citronix.mapper;

import net.yc.citronix.DTO.HarvestDetailDTO;
import net.yc.citronix.DTO.TreeDTO;
import net.yc.citronix.model.HarvestDetail;
import net.yc.citronix.model.Tree;
import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;

@Mapper(componentModel = "spring")
public interface HarvestDetailMapper extends BaseMapper<HarvestDetail, HarvestDetailDTO> {



}
