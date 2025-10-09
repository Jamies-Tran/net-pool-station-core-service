package net.pool.station.core.features.station.menu.controller.models;

import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.bootstrap.configuration.mapper.ModelMapper;
import net.pool.station.core.domain.station.menu.StationMenu;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface StationMenuResponseMapper extends ModelMapper<StationMenuResponse, StationMenu> {
}
