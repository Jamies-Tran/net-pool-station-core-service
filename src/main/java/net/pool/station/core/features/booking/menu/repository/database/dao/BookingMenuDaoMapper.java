package net.pool.station.core.features.booking.menu.repository.database.dao;

import net.pool.station.core.bootstrap.configuration.mapper.DaoMapper;
import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.domain.booking.menu.BookingMenu;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface BookingMenuDaoMapper extends DaoMapper<BookingMenuDao, BookingMenu> {
}
