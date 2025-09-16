package net.pool.station.core.features.station.log.repository.database;

import net.pool.station.core.bootstrap.configuration.mapper.EntityMapper;
import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.domain.station.log.StationLog;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface StationLogEntityMapper extends EntityMapper<StationLogEntity, StationLog> {
}
