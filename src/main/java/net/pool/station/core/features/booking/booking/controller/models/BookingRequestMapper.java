package net.pool.station.core.features.booking.booking.controller.models;

import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.bootstrap.configuration.mapper.ModelMapper;
import net.pool.station.core.domain.booking.Booking;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface BookingRequestMapper extends ModelMapper<BookingRequest, Booking> {
}
