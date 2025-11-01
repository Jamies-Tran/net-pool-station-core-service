package net.pool.station.core.features.game.controller.models;

import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.bootstrap.configuration.mapper.ModelMapper;
import net.pool.station.core.domain.game.Game;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface GameResponseMapper extends ModelMapper<GameResponse, Game> {
}
