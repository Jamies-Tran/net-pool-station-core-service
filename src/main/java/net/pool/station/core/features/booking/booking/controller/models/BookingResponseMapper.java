package net.pool.station.core.features.booking.booking.controller.models;

import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.bootstrap.configuration.mapper.ModelMapper;
import net.pool.station.core.domain.booking.Booking;
import net.pool.station.core.features.account.account.controller.models.AccountResponseMapper;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class, uses = {AccountResponseMapper.class})
public interface BookingResponseMapper extends ModelMapper<BookingResponse, Booking> {
}
