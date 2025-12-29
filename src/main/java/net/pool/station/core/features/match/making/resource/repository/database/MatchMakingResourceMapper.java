package net.pool.station.core.features.match.making.resource.repository.database;

import net.pool.station.core.bootstrap.configuration.mapper.EntityMapper;
import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.domain.match.making.resource.MatchMakingResource;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface MatchMakingResourceMapper extends EntityMapper<MatchMakingResourceEntity, MatchMakingResource> {
}
