package net.pool.station.core.features.station.space.repository.database;

import net.pool.station.core.bootstrap.configuration.mapper.EntityMapper;
import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.domain.station.space.StationSpace;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapStructConfig.class)
public interface StationSpaceEntityMapper extends EntityMapper<StationSpaceEntity, StationSpace> {
    void update(@MappingTarget StationSpaceEntity entity, StationSpace dto);
}
