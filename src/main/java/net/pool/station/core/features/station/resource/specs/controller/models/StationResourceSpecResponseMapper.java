package net.pool.station.core.features.station.resource.specs.controller.models;

import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.bootstrap.configuration.mapper.ModelMapper;
import net.pool.station.core.domain.station.resource.specs.StationResourceSpec;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface StationResourceSpecResponseMapper extends ModelMapper<StationResourceSpecResponse, StationResourceSpec> {
}
