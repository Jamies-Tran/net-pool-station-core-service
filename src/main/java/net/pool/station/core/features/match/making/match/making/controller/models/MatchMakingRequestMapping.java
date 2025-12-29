package net.pool.station.core.features.match.making.match.making.controller.models;

import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.bootstrap.configuration.mapper.ModelMapper;
import net.pool.station.core.domain.match.making.MatchMaking;
import org.mapstruct.Mapper;

import java.time.LocalDateTime;

@Mapper(config = MapStructConfig.class)
public interface MatchMakingRequestMapping extends ModelMapper<MatchMakingRequest, MatchMaking> {
}
