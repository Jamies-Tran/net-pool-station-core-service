package net.pool.station.core.features.booking.menu.repository.database.dao;

import net.pool.station.core.domain.booking.menu.BookingMenuId;

public interface BookingMenuDao {
    BookingMenuId getId();

    String getMenuCode();

    String getMenuName();

    String getTypeCode();

    String getTypeName();

    Integer getPrice();
}
