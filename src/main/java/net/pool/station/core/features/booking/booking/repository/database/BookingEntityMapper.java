package net.pool.station.core.features.booking.booking.repository.database;

import net.pool.station.core.bootstrap.configuration.mapper.EntityMapper;
import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.domain.booking.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapStructConfig.class)
public interface BookingEntityMapper extends EntityMapper<BookingEntity, Booking> {
    void update(@MappingTarget BookingEntity target, Booking source);
}
