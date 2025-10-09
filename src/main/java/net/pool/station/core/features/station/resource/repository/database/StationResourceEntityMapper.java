package net.pool.station.core.features.station.resource.repository.database;

import net.pool.station.core.bootstrap.configuration.mapper.EntityMapper;
import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.domain.station.resource.StationResource;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapStructConfig.class)
public interface StationResourceEntityMapper extends EntityMapper<StationResourceEntity, StationResource> {
    void update(@MappingTarget StationResourceEntity target, StationResource source);
}
