package net.yc.citronix.mapper;
import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;

import java.util.List;


@MapperConfig(componentModel = "spring",unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface BaseMapper<E, D> {

    D toDTO(E entity);

    E toEntity(D dto);

    List<D> toDTOs(List<E> entities);

    List<E> toEntities(List<D> dtos);
}
