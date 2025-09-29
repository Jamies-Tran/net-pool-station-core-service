package net.pool.station.core.features.area.type.controller.models;

import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.bootstrap.configuration.mapper.ModelMapper;
import net.pool.station.core.domain.area.type.AreaType;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface AreaTypeRequestMapper extends ModelMapper<AreaTypeRequest, AreaType> {
}
