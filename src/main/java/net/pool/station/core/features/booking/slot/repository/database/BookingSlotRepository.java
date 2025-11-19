package net.pool.station.core.features.booking.slot.repository.database;

import net.pool.station.core.features.booking.slot.repository.database.dao.BookingSlotDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingSlotRepository extends JpaRepository<BookingSlotEntity, BookingSlotEntityId> {
    @Query("""
        SELECT 
                bs.bookingSlotId AS bookingSlotId,
                ts.begin AS begin, 
                ts.end AS end, 
                ts.periodCode AS periodCode, 
                ts.periodName AS periodName
        FROM BookingSlotEntity bs
        INNER JOIN TimeSlotEntity ts ON bs.bookingSlotId.timeSlotId = ts.timeSlotId
        WHERE bs.bookingSlotId.bookingId = :bookingId
        """)
    List<BookingSlotDao> findAllByBookingId(Long bookingId);
}
