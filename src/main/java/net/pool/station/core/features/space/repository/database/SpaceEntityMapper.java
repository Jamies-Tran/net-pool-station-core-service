package net.pool.station.core.features.space.repository.database;

import net.pool.station.core.bootstrap.configuration.mapper.EntityMapper;
import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.domain.space.Space;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapStructConfig.class)
public interface SpaceEntityMapper extends EntityMapper<SpaceEntity, Space> {
    void update(@MappingTarget SpaceEntity entity, Space dto);
}
