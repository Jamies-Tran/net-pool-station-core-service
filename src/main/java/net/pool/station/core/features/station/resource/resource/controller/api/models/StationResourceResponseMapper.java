package net.pool.station.core.features.station.resource.resource.controller.api.models;

import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.bootstrap.configuration.mapper.ModelMapper;
import net.pool.station.core.domain.station.resource.StationResource;
import net.pool.station.core.features.station.resource.specs.controller.models.StationResourceSpecResponseMapper;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class, uses = StationResourceSpecResponseMapper.class)
public interface StationResourceResponseMapper extends ModelMapper<StationResourceResponse, StationResource> {
}
