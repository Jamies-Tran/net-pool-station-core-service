package net.pool.station.core.features.booking.menu.repository.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingMenuRepository extends JpaRepository<BookingMenuEntity, BookingMenuEntityId> {
}
