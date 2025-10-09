package net.pool.station.core.features.station.menu.repository.database;

import net.pool.station.core.bootstrap.configuration.mapper.EntityMapper;
import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.domain.station.menu.StationMenu;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapStructConfig.class)
public interface StationMenuEntityMapper extends EntityMapper<StationMenuEntity, StationMenu> {
    void update(@MappingTarget StationMenuEntity target, StationMenu source);
}
