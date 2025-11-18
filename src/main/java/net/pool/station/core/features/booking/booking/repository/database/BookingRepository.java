package net.pool.station.core.features.booking.booking.repository.database;

import net.pool.station.core.domain.booking.BookingCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Long> {
    Optional<BookingEntity> findByBookingIdAndDeletedFalse(Long bookingId);

    @Query("""
        SELECT b
        FROM BookingEntity b
        INNER JOIN ScheduleEntity sc ON b.scheduleId = sc.scheduleId
        WHERE b.deleted = false
            AND (:#{#criteria.dateRange().empty} = TRUE
                    OR sc.date BETWEEN :#{#criteria.startFrom()} AND :#{#criteria.endTo()})
            AND (:#{#criteria.accountId()} = 0
                    OR b.accountId = :#{#criteria.accountId()})
            AND (:#{#criteria.typeCodes().empty} = TRUE
                    OR b.typeCode IN :#{#criteria.typeCodes()})
            AND (:#{#criteria.statusCodes().empty} = TRUE
                    OR b.statusCode IN :#{#criteria.statusCodes()})
            AND (:#{#criteria.search().empty} = TRUE
                    OR b.bookingCode ILIKE %:#{#criteria.search()}%)
        """)
    Page<BookingEntity> findAll(BookingCriteria criteria, Pageable pageable);
}
