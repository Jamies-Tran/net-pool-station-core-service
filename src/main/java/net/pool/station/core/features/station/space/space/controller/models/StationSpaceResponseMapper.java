package net.pool.station.core.features.station.space.space.controller.models;

import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.bootstrap.configuration.mapper.ModelMapper;
import net.pool.station.core.domain.station.space.StationSpace;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface StationSpaceResponseMapper extends ModelMapper<StationSpaceResponse, StationSpace> {
}
