package net.pool.station.core.features.booking.menu.repository.database;

import net.pool.station.core.bootstrap.configuration.mapper.EntityMapper;
import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.domain.booking.menu.BookingMenu;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface BookingMenuEntityMapper extends EntityMapper<BookingMenuEntity, BookingMenu> {
}
