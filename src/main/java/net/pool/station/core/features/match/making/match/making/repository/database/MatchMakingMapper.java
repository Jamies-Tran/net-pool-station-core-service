package net.pool.station.core.features.match.making.match.making.repository.database;

import net.pool.station.core.bootstrap.configuration.mapper.EntityMapper;
import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.domain.match.making.MatchMaking;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapStructConfig.class)
public interface MatchMakingMapper extends EntityMapper<MatchMakingEntity, MatchMaking> {
    void update(@MappingTarget MatchMakingEntity target, MatchMaking source);
}
