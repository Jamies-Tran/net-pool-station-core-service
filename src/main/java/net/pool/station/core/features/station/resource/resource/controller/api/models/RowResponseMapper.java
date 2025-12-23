package net.pool.station.core.features.station.resource.resource.controller.api.models;

import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.bootstrap.configuration.mapper.ModelMapper;
import net.pool.station.core.domain.station.resource.Row;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface RowResponseMapper extends ModelMapper<RowResponse, Row> {
}
