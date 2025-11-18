package net.pool.station.core.features.booking.resource.repository.database.dao;

import net.pool.station.core.bootstrap.configuration.mapper.DaoMapper;
import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.domain.booking.resource.BookingResource;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface BookingResourceDaoMapper extends DaoMapper<BookingResourceDao, BookingResource> {
}
