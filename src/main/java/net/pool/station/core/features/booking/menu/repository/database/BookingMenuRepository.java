package net.pool.station.core.features.booking.menu.repository.database;

import net.pool.station.core.features.booking.menu.repository.database.dao.BookingMenuDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingMenuRepository extends JpaRepository<BookingMenuEntity, BookingMenuEntityId> {
    @Query("""
        SELECT
            bm.bookingMenuId AS bookingMenuId,
            sm.menuCode AS menuCode,
            sm.menuName AS menuName,
            sm.typeCode AS typeCode,
            sm.typeName AS typeName,
            sm.price AS price
        FROM BookingMenuEntity bm
        INNER JOIN StationMenuEntity sm ON bm.bookingMenuId.stationMenuId = sm.stationMenuId
        WHERE bm.bookingMenuId.bookingId = :bookingId
        """)
    List<BookingMenuDao> findAllByBookingId(Long bookingId);


    @Query("""
        SELECT bm
        FROM BookingMenuEntity bm
        WHERE bm.bookingMenuId.bookingId = :bookingId
        """)
    List<BookingMenuEntity> findListByBookingId(Long bookingId);
}
