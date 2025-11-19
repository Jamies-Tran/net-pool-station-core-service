package net.pool.station.core.features.booking.slot.repository.database.dao;

import net.pool.station.core.bootstrap.configuration.mapper.DaoMapper;
import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.domain.booking.slot.BookingSlot;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface BookingSlotDaoMapper extends DaoMapper<BookingSlotDao, BookingSlot> {
}
