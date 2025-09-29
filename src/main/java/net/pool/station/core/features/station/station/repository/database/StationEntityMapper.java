package net.pool.station.core.features.station.station.repository.database;

import net.pool.station.core.bootstrap.configuration.mapper.EntityMapper;
import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.domain.station.Station;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapStructConfig.class)
public interface StationEntityMapper extends EntityMapper<StationEntity, Station> {
    void update(@MappingTarget StationEntity entity, Station dto);
}
