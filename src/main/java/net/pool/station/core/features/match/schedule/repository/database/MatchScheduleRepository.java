package net.pool.station.core.features.match.schedule.repository.database;

import net.pool.station.core.features.match.schedule.repository.database.dao.MatchScheduleDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchScheduleRepository extends JpaRepository<MatchScheduleEntity, MatchScheduleEntityId> {

    @Query("""
        DELETE
        FROM MatchScheduleEntity msc
        WHERE msc.id.matchMakingId = :matchMakingId
        """)
    @Modifying
    void deleteAllByMatchMakingId(Long matchMakingId);

    @Query("""
        SELECT 
                msc.id AS id,
                sc.date AS date
        FROM MatchScheduleEntity msc
        INNER JOIN ScheduleEntity sc ON msc.id.scheduleId = sc.scheduleId AND sc.deleted = FALSE
        """)
    List<MatchScheduleDao> findAllByMatchMakingId(Long matchMakingId);
}
