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

    @Query("""
        SELECT bs
        FROM BookingSlotEntity bs
        WHERE bs.bookingSlotId.bookingId = :bookingId
        """)
    List<BookingSlotEntity> findListByBookingId(Long bookingId);

    @Query("""
        SELECT 
                bs.bookingSlotId AS bookingSlotId,
                t.begin AS begin,
                t.end AS end,
                sc.date AS date
        FROM BookingSlotEntity bs
        INNER JOIN TimeSlotEntity t ON bs.bookingSlotId.timeSlotId = t.timeSlotId
        INNER JOIN ScheduleEntity sc ON t.scheduleId = sc.scheduleId
        INNER JOIN BookingEntity b ON bs.bookingSlotId.bookingId = b.bookingId
        INNER JOIN StationResourceEntity s ON b.stationResourceId = s.stationResourceId
        WHERE s.stationResourceId = :stationResourceId AND bs.bookingSlotId.timeSlotId IN :timeSlotIds
        """)
    List<BookingSlotDao> findAllByStationResourceIdAndTimeSlotIdIn(Long stationResourceId, List<Long> timeSlotIds);
}
