package net.yc.citronix.mapper;

import net.yc.citronix.DTO.TreeDTO;
import net.yc.citronix.model.Tree;
import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;

@Mapper(componentModel = "spring")
public interface TreeMapper extends BaseMapper<Tree, TreeDTO> {
}
