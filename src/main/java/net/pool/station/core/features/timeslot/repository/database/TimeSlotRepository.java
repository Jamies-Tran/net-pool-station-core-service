package net.pool.station.core.features.timeslot.repository.database;

import net.pool.station.core.domain.timeslot.TimeSlotCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlotEntity, Long> {
    @Query("""
        SELECT t
        FROM TimeSlotEntity t
        WHERE t.scheduleId = :#{#criteria.scheduleId()}
            AND (:#{#criteria.periodCodes().empty} = TRUE
                    OR t.periodCode IN :#{#criteria.periodCodes()})
            AND (:#{#criteria.statusCodes().empty} = TRUE
                    OR t.statusCode IN :#{#criteria.statusCodes()})
        """)
    Page<TimeSlotEntity> findAll(TimeSlotCriteria criteria, Pageable pageable);

    List<TimeSlotEntity> findAllByScheduleIdAndStatusCode(Long scheduleId, String statusCode);

    List<TimeSlotEntity> findAllByScheduleId(Long scheduleId);

    void deleteAllByScheduleId(Long scheduleId);
}
