package net.pool.station.core.features.match.making.resource.repository.database.dao;

import net.pool.station.core.bootstrap.configuration.mapper.DaoMapper;
import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.domain.match.making.resource.MatchMakingResource;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface MatchMakingResourceDaoMapper extends DaoMapper<MatchMakingResourceDao,
        MatchMakingResource> {
}
