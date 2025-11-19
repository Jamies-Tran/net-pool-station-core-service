package net.pool.station.core.features.booking.resource.repository.database.dao;

import net.pool.station.core.domain.booking.resource.BookingResourceId;

public interface BookingResourceDao {
    BookingResourceId bookingResourceId();

    String getResourceCode();

    String getResourceName();

    Integer getPrice();

    String getTypeCode();

    String getTypeName();
}
