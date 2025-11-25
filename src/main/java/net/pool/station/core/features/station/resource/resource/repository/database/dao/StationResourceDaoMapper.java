package net.pool.station.core.features.station.resource.resource.repository.database.dao;

import net.pool.station.core.bootstrap.configuration.mapper.DaoMapper;
import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.domain.station.resource.StationResource;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface StationResourceDaoMapper extends DaoMapper<StationResourceDao, StationResource> {
}
