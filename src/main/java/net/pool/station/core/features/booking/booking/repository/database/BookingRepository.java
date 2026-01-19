package net.pool.station.core.features.booking.booking.repository.database;

import net.pool.station.core.domain.booking.BookingCriteria;
import net.pool.station.core.features.booking.booking.repository.database.dao.TimeSlotDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Long> {
    Optional<BookingEntity> findByBookingIdAndDeletedFalse(Long bookingId);

    @Query("""
        SELECT b
        FROM BookingEntity b
        INNER JOIN ScheduleEntity sc ON b.scheduleId = sc.scheduleId
        INNER JOIN StationResourceEntity sr ON b.stationResourceId = sr.stationResourceId
        INNER JOIN AreaEntity a ON sr.areaId = a.areaId
        INNER JOIN StationSpaceEntity ss ON a.stationSpaceId = ss.stationSpaceId
        INNER JOIN StationEntity s ON ss.stationId = s.stationId
        WHERE b.deleted = false
            AND (:#{#criteria.dateRange().empty} = TRUE
                    OR sc.date BETWEEN :#{#criteria.startFrom()} AND :#{#criteria.endTo()})
            AND (:#{#criteria.accountId().empty} = TRUE
                    OR b.createdBy = :#{#criteria.accountId()})
            AND (:#{#criteria.typeCodes().empty} = TRUE
                    OR b.typeCode IN :#{#criteria.typeCodes()})
            AND (:#{#criteria.statusCodes().empty} = TRUE
                    OR b.statusCode IN :#{#criteria.statusCodes()})
            AND (:#{#criteria.search().empty} = TRUE
                    OR b.bookingCode ILIKE %:#{#criteria.search()}%)
            AND (:#{#criteria.stationId()} = 0
                    OR s.stationId = :#{#criteria.stationId()})
        """)
    Page<BookingEntity> findAll(BookingCriteria criteria, Pageable pageable);

    @Query("""
        SELECT 
                t.begin AS begin,
                t.end AS end
        FROM TimeSlotEntity t
        WHERE t.timeSlotId IN :timeSlotIds
        """)
    List<TimeSlotDao> findTimeSlotByIdIn(List<Long> timeSlotIds);

    @Query("""
        SELECT sc.date
        FROM ScheduleEntity sc
        WHERE sc.scheduleId = :scheduleId
        """)
    Optional<LocalDate> findBookingDateByScheduleId(Long scheduleId);

    @Query("""
        SELECT w.walletId
        FROM BookingEntity b
        INNER JOIN StationResourceEntity sr ON b.stationResourceId = sr.stationResourceId
        INNER JOIN AreaEntity a ON a.areaId = sr.areaId
        INNER JOIN StationSpaceEntity ss ON ss.stationSpaceId = a.stationSpaceId
        INNER JOIN StationEntity s ON ss.stationId = s.stationId
        INNER JOIN StationAccountEntity sa ON sa.stationAccountId.stationId = s.stationId
        INNER JOIN AccountEntity ac ON sa.stationAccountId.accountId = ac.accountId
        INNER JOIN RoleEntity r ON ac.roleId = r.roleId 
                AND r.roleCode = :#{T(net.pool.station.core.bootstrap.enums.ERole).STATION_OWNER.getCode()}
        INNER JOIN WalletEntity w ON w.accountId = ac.accountId
        WHERE sr.stationResourceId = :stationResourceId
        ORDER BY w.walletId
        LIMIT 1
        """)
    Optional<Long> findStationOwnerWalletIdByStationResourceId(Long stationResourceId);
}
