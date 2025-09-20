package net.pool.station.core.features.space.controller.models;

import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.bootstrap.configuration.mapper.ModelMapper;
import net.pool.station.core.domain.space.Space;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface SpaceRequestMapper extends ModelMapper<SpaceRequest, Space> {
}
