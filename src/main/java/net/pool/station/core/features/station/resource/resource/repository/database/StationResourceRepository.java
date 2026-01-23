package net.pool.station.core.features.station.resource.resource.repository.database;

import net.pool.station.core.domain.station.resource.StationResourceCriteria;
import net.pool.station.core.features.station.resource.resource.repository.database.dao.StationResourceDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface StationResourceRepository extends JpaRepository<StationResourceEntity, Long> {
    Boolean existsByAreaIdAndResourceCodeAndDeletedFalse(Long stationId, String resourceCode);

    List<StationResourceEntity> findAllByAreaId(Long areaId);

    @Query("""
        SELECT
                sr.stationResourceId AS stationResourceId,
                sr.areaId AS areaId,
                sr.rowCode AS rowCode,
                sr.rowName AS rowName,
                sr.resourceCode AS resourceCode,
                sr.resourceName AS resourceName,
                sr.typeCode AS typeCode,
                sr.typeName AS typeName,
                sr.statusCode AS statusCode,
                sr.statusName AS statusName,
                a.price AS price,
                w.balance > 0 AS allowDirectPayment
        FROM StationResourceEntity sr
        INNER JOIN AreaEntity a ON sr.areaId = a.areaId
        INNER JOIN StationSpaceEntity ss ON a.stationSpaceId = ss.stationSpaceId
        INNER JOIN StationEntity s ON ss.stationId = s.stationId
        INNER JOIN StationAccountEntity sa ON s.stationId = sa.stationAccountId.stationId
        INNER JOIN AccountEntity ac ON sa.stationAccountId.accountId = ac.accountId
        INNER JOIN RoleEntity r ON ac.roleId = r.roleId 
                AND r.roleCode = :#{T(net.pool.station.core.bootstrap.enums.ERole).STATION_OWNER.getCode()}
        INNER JOIN WalletEntity w ON ac.accountId = w.accountId
        WHERE sr.deleted = FALSE 
                AND sr.stationResourceId = :stationResourceId
        """)
    Optional<StationResourceDao> findByStationResourceId(Long stationResourceId);


    Optional<StationResourceEntity> findByStationResourceIdAndDeletedFalse(Long stationResourceId);

    @Query("""
        SELECT 
                s.stationResourceId AS stationResourceId,
                s.rowCode AS rowCode,
                s.rowName AS rowName,
                s.areaId AS areaId,
                s.resourceCode AS resourceCode,
                s.resourceName AS resourceName,
                s.typeCode AS typeCode,
                s.typeName AS typeName,
                s.statusCode AS statusCode,
                s.statusName AS statusName,
                s.displayOrder AS displayOrder,
                a.price AS price
        FROM StationResourceEntity s
        INNER JOIN AreaEntity a ON a.areaId = s.areaId
        WHERE (:#{#criteria.areaId()} = 0
                OR s.areaId = :#{#criteria.areaId()})
              AND (:#{#criteria.stationSpaceId()} = 0
                      OR a.stationSpaceId = :#{#criteria.stationSpaceId()})
              AND (:#{#criteria.search().empty} = TRUE
                      OR (s.resourceName ILIKE %:#{#criteria.search()}%
                              OR s.resourceCode = :#{#criteria.search()}
                              OR s.rowName ILIKE %:#{#criteria.search()}%))
              AND (:#{#criteria.typeCodes().empty} = TRUE
                      OR s.typeCode IN :#{#criteria.typeCodes()})
              AND (:#{#criteria.statusCodes().empty} = TRUE
                      OR s.statusCode IN :#{#criteria.statusCodes()})
        """)
    Page<StationResourceDao> findAll(StationResourceCriteria criteria, Pageable pageable);

    @Query("""
        SELECT COUNT(a) > 0
        FROM AreaEntity a
        WHERE a.areaId = :areaId
        """)
    Boolean validateToken(Long areaId);

    @Query("""
        SELECT SUM(COALESCE(a.price, 0) )
        FROM StationResourceEntity sr
        INNER JOIN AreaEntity a ON sr.areaId = a.areaId
        WHERE sr.stationResourceId IN :stationResourceIds
        """)
    Integer totalPriceByStationResourceIdIn(List<Long> stationResourceIds);

    @Query("""

            SELECT DISTINCT sr.stationResourceId
                    FROM StationResourceEntity sr
                    
                    LEFT JOIN BookingEntity b
                           ON b.stationResourceId = sr.stationResourceId
                          AND b.statusCode IN ('PENDING', 'NEW', 'PROCESSING')
                          AND DATE(b.startAt) <= :localDate
                          AND DATE(b.endAt) >= :localDate
                    
                    LEFT JOIN BookingSlotEntity bs
                           ON bs.bookingSlotId.bookingId = b.bookingId
                    
                    LEFT JOIN TimeSlotEntity bst
                           ON bst.timeSlotId = bs.bookingSlotId.timeSlotId
                          AND :#{#localTime.get(0)} < bst.end
                          AND :#{#localTime.get(1)} > bst.begin
                    
                    LEFT JOIN MatchMakingResourceEntity mr
                           ON mr.id.stationResourceId = sr.stationResourceId
                    
                    LEFT JOIN MatchMakingEntity m
                           ON m.matchMakingId = mr.id.matchMakingId
                          AND m.statusCode IN ('DRAFT', 'PENDING', 'PREPARE_START', 'STARTED')
                          AND (
                                (m.startAt <= :localDate AND m.expiredAt >= :localDate)
                                OR DATE(m.playAt) = :localDate
                              )
                    
                    LEFT JOIN MatchMakingSlotEntity ms
                           ON ms.id.matchMakingId = m.matchMakingId
                    
                    LEFT JOIN TimeSlotEntity mst
                           ON mst.timeSlotId = ms.id.timeSlotId
                          AND :#{#localTime.get(0)} < mst.end
                          AND :#{#localTime.get(1)} > mst.begin
                    
                    WHERE
                          (b.bookingId IS NOT NULL AND bst.timeSlotId IS NOT NULL)
                       OR (m.matchMakingId IS NOT NULL AND mst.timeSlotId IS NOT NULL)
                    
            """)
    List<Long> findAvailableByLocalDateAndLocalTimeIn(LocalDate localDate, List<LocalTime> localTime);


    @Query("""
        SELECT sr
        FROM StationResourceEntity sr
        INNER JOIN AreaEntity a ON a.areaId = sr.areaId
        INNER JOIN StationSpaceEntity ss ON ss.stationSpaceId = a.stationSpaceId
        WHERE sr.deleted = false
                AND ss.stationSpaceId = :stationSpaceId
                        AND sr.stationResourceId NOT IN :stationResourceIds
        """)
    List<StationResourceEntity> findAllByStationSpaceIdAndStationResourceIdNotInAndDeletedFalse(Long stationSpaceId, List<Long> stationResourceIds);
}
