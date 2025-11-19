package net.pool.station.core.features.booking.slot.repository.database;

import net.pool.station.core.bootstrap.configuration.mapper.EntityMapper;
import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.domain.booking.slot.BookingSlot;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface BookingSlotEntityMapper extends EntityMapper<BookingSlotEntity, BookingSlot> {
}
