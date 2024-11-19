package net.yc.citronix.mapper;

import net.yc.citronix.DTO.FieldDTO;
import net.yc.citronix.model.Field;
import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;

@Mapper(config = BaseMapperConfig.class)
public interface FieldMapper extends BaseMapper<Field, FieldDTO> {
}
