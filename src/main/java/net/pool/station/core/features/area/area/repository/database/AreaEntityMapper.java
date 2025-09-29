package net.pool.station.core.features.area.area.repository.database;

import net.pool.station.core.bootstrap.configuration.mapper.EntityMapper;
import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.domain.area.Area;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapStructConfig.class)
public interface AreaEntityMapper extends EntityMapper<AreaEntity, Area> {
    void update(@MappingTarget AreaEntity entity, Area dto);
}
