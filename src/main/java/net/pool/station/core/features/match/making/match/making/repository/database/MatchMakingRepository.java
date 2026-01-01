package net.pool.station.core.features.match.making.match.making.repository.database;

import net.pool.station.core.domain.match.making.MatchMakingCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MatchMakingRepository extends JpaRepository<MatchMakingEntity, Long> {
    Optional<MatchMakingEntity> findByMatchMakingIdAndDeletedFalse(Long matchMakingId);

    @Query("""
        SELECT m
        FROM MatchMakingEntity m
        INNER JOIN ScheduleEntity s ON m.scheduleId = s.scheduleId
        WHERE m.deleted = FALSE
            AND (:#{#criteria.search().empty} = TRUE
                    OR m.matchMakingCode ILIKE %:#{#criteria.search()}%)
            AND (:#{#criteria.timeRangeStartAt().empty} = TRUE
                    OR s.date BETWEEN :#{#criteria.timeRangeStartAt().get(0)} 
                            AND :#{#criteria.timeRangeStartAt().get(1)})
            AND (:#{#criteria.statusCodes().empty} = TRUE
                    OR m.statusCode IN :#{#criteria.statusCodes()})
        """)
    Page<MatchMakingEntity> findAll(MatchMakingCriteria criteria, Pageable pageable);

    @Query("""
        SELECT DISTINCT w.walletId
        FROM MatchMakingEntity mm
        INNER JOIN StationEntity s ON mm.stationId = s.stationId
        INNER JOIN StationAccountEntity sa ON s.stationId = sa.stationAccountId.stationId
        INNER JOIN AccountEntity a ON a.accountId = sa.stationAccountId.accountId
        INNER JOIN RoleEntity r ON a.roleId = r.roleId
                AND r.roleCode = :#{T(net.pool.station.core.bootstrap.enums.ERole).STATION_OWNER.getCode()}
        INNER JOIN WalletEntity w ON w.accountId = a.accountId
        WHERE mm.stationId = :stationId
        """)
    Optional<Long> findOwnerWalletIdByStationId(Long stationId);
}
