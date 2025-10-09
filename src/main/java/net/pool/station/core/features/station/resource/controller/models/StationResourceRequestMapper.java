package net.pool.station.core.features.station.resource.controller.models;

import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.bootstrap.configuration.mapper.ModelMapper;
import net.pool.station.core.domain.station.resource.StationResource;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface StationResourceRequestMapper extends ModelMapper<StationResourceRequest, StationResource> {
}
