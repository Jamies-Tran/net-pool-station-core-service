package net.pool.station.core.features.station.space.space.repository.database;

import net.pool.station.core.domain.station.space.StationSpaceCriteria;
import net.pool.station.core.domain.station.space.StationSpaceId;
import net.pool.station.core.features.station.space.space.repository.database.dao.StationSpaceAllowDirectPaymentDao;
import net.pool.station.core.features.station.space.space.repository.database.dao.StationSpaceDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StationSpaceRepository extends JpaRepository<StationSpaceEntity, Long> {
    @Query("""
        SELECT COUNT(ss) > 0
        FROM StationSpaceEntity ss
        WHERE ss.deleted = FALSE
                AND ss.spaceId = :spaceId
                AND ss.spaceName = :spaceName
        """)
    Boolean existsByStationIdAndSpaceName(Long spaceId, String spaceName);

    @Query("""
        SELECT COUNT(ss) > 0
        FROM StationSpaceEntity ss
        WHERE ss.deleted = FALSE
                AND ss.spaceId = :spaceId
                AND ss.spaceCode = :spaceCode
        """)
    Boolean existsByStationIdAndSpaceCode(Long spaceId, String spaceCode);

    @Query("""
        SELECT 
                ss AS stationSpace,
                w.directPayment AS allowDirectPayment,
                sp.metadata AS metadata
        FROM StationSpaceEntity ss
        INNER JOIN SpaceEntity sp ON ss.spaceId = sp.spaceId
        INNER JOIN StationEntity s ON ss.stationId = s.stationId
        INNER JOIN StationAccountEntity sa ON s.stationId = sa.stationAccountId.stationId
        INNER JOIN AccountEntity a ON sa.stationAccountId.accountId = a.accountId
        INNER JOIN RoleEntity r ON a.roleId = r.roleId 
                AND r.roleCode = :#{T(net.pool.station.core.bootstrap.enums.ERole).STATION_OWNER.getCode()}
        INNER JOIN WalletEntity w ON a.accountId = w.accountId
        WHERE ss.deleted = FALSE AND ss.stationSpaceId = :stationSpaceId
        """)
    Optional<StationSpaceAllowDirectPaymentDao> findByStationSpaceIdWithAllowDirectPayment(Long stationSpaceId);

    Optional<StationSpaceEntity> findByStationSpaceIdAndDeletedFalse(Long stationSpaceId);

    @Query("""
        SELECT 
                ss AS stationSpace,
                s.metadata AS metadata
        FROM StationSpaceEntity ss
        INNER JOIN SpaceEntity s ON ss.spaceId = s.spaceId
        WHERE ss.deleted = FALSE
                AND (:#{#criteria.search().empty} = TRUE
                        OR (ss.spaceName ILIKE %:#{#criteria.search()}%
                                OR ss.spaceCode ILIKE %:#{#criteria.search()}%))
                AND (:#{#criteria.stationId()} = 0
                        OR ss.stationId = :#{#criteria.stationId()})
                AND (:#{#criteria.typeCodes().empty} = TRUE
                        OR s.typeCode IN :#{#criteria.typeCodes()})
                AND (:#{#criteria.statusCodes().empty} = TRUE
                        OR s.statusCode IN :#{#criteria.statusCodes()})
        """)
    Page<StationSpaceDao> findAll(StationSpaceCriteria criteria, Pageable pageable);
}
