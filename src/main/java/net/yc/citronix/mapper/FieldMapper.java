package net.yc.citronix.mapper;

import net.yc.citronix.DTO.FieldDTO;
import net.yc.citronix.model.Field;
import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;

@Mapper(componentModel = "spring")
public interface FieldMapper extends BaseMapper<Field, FieldDTO> {
}
