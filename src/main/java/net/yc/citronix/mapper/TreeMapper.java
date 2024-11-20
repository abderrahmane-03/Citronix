package net.yc.citronix.mapper;

import net.yc.citronix.DTO.TreeDTO;
import net.yc.citronix.model.Tree;
import org.mapstruct.Mapper;

@Mapper(config = BaseMapperConfig.class)
public interface TreeMapper extends BaseMapper<Tree, TreeDTO> {
}
