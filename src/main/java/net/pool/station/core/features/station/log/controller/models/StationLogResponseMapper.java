package net.pool.station.core.features.station.log.controller.models;

import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.bootstrap.configuration.mapper.ModelMapper;
import net.pool.station.core.domain.station.log.StationLog;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface StationLogResponseMapper extends ModelMapper<StationLogResponse, StationLog> {
}
