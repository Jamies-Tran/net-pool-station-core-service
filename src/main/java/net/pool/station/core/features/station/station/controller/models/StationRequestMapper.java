package net.pool.station.core.features.station.station.controller.models;

import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.bootstrap.configuration.mapper.ModelMapper;
import net.pool.station.core.domain.station.Station;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface StationRequestMapper extends ModelMapper<StationRequest, Station> {
}
