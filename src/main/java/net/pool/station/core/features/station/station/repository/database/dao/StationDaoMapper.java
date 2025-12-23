package net.pool.station.core.features.station.station.repository.database.dao;

import net.pool.station.core.bootstrap.configuration.mapper.DaoMapper;
import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.domain.station.Station;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface StationDaoMapper extends DaoMapper<StationDao, Station> {
}
