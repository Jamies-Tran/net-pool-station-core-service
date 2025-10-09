package net.pool.station.core.features.area.area.controller.models;

import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.bootstrap.configuration.mapper.ModelMapper;
import net.pool.station.core.domain.area.Area;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface AreaResponseMapper extends ModelMapper<AreaResponse, Area> {
}
