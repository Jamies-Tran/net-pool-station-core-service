package net.pool.station.core.features.game.repository.database;

import net.pool.station.core.bootstrap.configuration.mapper.EntityMapper;
import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.domain.game.Game;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapStructConfig.class)
public interface GameEntityMapper extends EntityMapper<GameEntity, Game> {
    @Mapping(target = "gameCode", ignore = true)
    void update(@MappingTarget GameEntity target, Game source);
}
