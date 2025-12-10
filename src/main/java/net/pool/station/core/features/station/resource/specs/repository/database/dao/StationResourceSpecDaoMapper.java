package net.pool.station.core.features.station.resource.specs.repository.database.dao;

import net.pool.station.core.bootstrap.configuration.mapper.DaoMapper;
import net.pool.station.core.domain.station.resource.specs.StationResourceSpec;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StationResourceSpecDaoMapper extends DaoMapper<StationResourceSpecDao, StationResourceSpec> {
}
