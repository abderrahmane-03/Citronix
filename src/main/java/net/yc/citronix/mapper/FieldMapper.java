package net.yc.citronix.mapper;

import net.yc.citronix.DTO.FieldDTO;
import net.yc.citronix.model.Field;
import org.mapstruct.Mapper;

@Mapper(config = BaseMapper.class)
public interface FieldMapper extends BaseMapper<Field, FieldDTO> {
}
