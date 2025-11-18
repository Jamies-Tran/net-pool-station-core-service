package net.pool.station.core.features.booking.resource.repository.database;

import net.pool.station.core.bootstrap.configuration.mapper.EntityMapper;
import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.domain.booking.resource.BookingResource;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface BookingResourceEntityMapper extends EntityMapper<BookingResourceEntity, BookingResource> {
}
