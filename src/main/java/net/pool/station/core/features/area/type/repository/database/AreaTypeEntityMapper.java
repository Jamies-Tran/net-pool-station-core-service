package net.pool.station.core.features.area.type.repository.database;

import net.pool.station.core.bootstrap.configuration.mapper.EntityMapper;
import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.domain.area.type.AreaType;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapStructConfig.class)
public interface AreaTypeEntityMapper extends EntityMapper<AreaTypeEntity, AreaType> {
    void update(@MappingTarget AreaTypeEntity entity, AreaType dto);
}
