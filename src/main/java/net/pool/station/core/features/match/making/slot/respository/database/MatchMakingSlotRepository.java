package net.pool.station.core.features.match.making.slot.respository.database;

import net.pool.station.core.features.match.making.slot.respository.database.dao.MatchMakingSlotDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchMakingSlotRepository extends JpaRepository<MatchMakingSlotEntity, MatchMakingSlotEntityId> {
    @Query("""
        DELETE
        FROM MatchMakingSlotEntity mms
        WHERE mms.id.matchMakingId = :matchMakingId
        """)
    @Modifying
    void deleteAllByMatchMakingId(Long matchMakingId);

    @Query("""
        SELECT
                mms.id AS id,
                ts.begin AS begin,
                ts.end AS end
        FROM MatchMakingSlotEntity mms
        INNER JOIN TimeSlotEntity ts ON mms.id.timeSlotId = ts.timeSlotId
        WHERE mms.id.matchMakingId = :matchMakingId
        """)
    List<MatchMakingSlotDao> findAllByMatchMakingId(Long matchMakingId);

    @Query("""
        SELECT
                ms.id AS id,
                t.begin AS begin,
                t.end AS end,
                sc.date AS startAt,
                m.expiredAt AS expiredAt,
                m.playAt AS playAt,
                m.statusCode AS matchMakingStatusCode
        FROM MatchMakingSlotEntity ms
        INNER JOIN MatchMakingResourceEntity mr ON ms.id.matchMakingId = mr.id.matchMakingId 
                AND mr.id.stationResourceId = :stationResourceId
        INNER JOIN TimeSlotEntity t ON ms.id.timeSlotId = t.timeSlotId
        INNER JOIN MatchMakingEntity m ON m.matchMakingId = ms.id.matchMakingId
        INNER JOIN ScheduleEntity sc ON sc.scheduleId = m.scheduleId
        WHERE m.statusCode IN :matchMakingStatusCodes
        """)
    List<MatchMakingSlotDao> findAllByStationResourceIdAndStatusCodeIn(
            Long stationResourceId, List<String> matchMakingStatusCodes);
}
