package net.pool.station.core.features.station.resource.specs.repository.database;

import net.pool.station.core.bootstrap.configuration.mapper.EntityMapper;
import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.domain.station.resource.specs.StationResourceSpec;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapStructConfig.class)
public interface StationResourceSpecEntityMapper extends EntityMapper<StationResourceSpecEntity, StationResourceSpec> {
    @Mapping(target = "areaId", ignore = true)
    void update(@MappingTarget StationResourceSpecEntity target, StationResourceSpec source);
}
