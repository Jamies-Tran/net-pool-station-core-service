package net.pool.station.core.features.station.station.repository.database;

import net.pool.station.core.domain.station.StationCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StationRepository extends JpaRepository<StationEntity, Long> {
    @Query("""
        SELECT s
        FROM StationEntity s
        WHERE s.deleted = FALSE
                AND s.stationId = :stationId
        """)
    Optional<StationEntity> findByStationId(Long stationId);

    @Query("""
        SELECT COUNT(s) > 0
        FROM StationEntity s
        WHERE s.deleted = false
                AND s.stationName = :stationName
        """)
    Boolean existsByStationName(String stationName);

    @Query("""
        SELECT s
        FROM StationEntity s
        WHERE s.deleted = FALSE
                AND (s.createdAt BETWEEN :#{#criteria.timeRange().get(0)}
                    AND :#{#criteria.timeRange().get(1)})
                AND (:#{#criteria.search().empty} = TRUE
                        OR (s.stationName ILIKE %:#{#criteria.search()}%
                                OR s.statusCode = :#{#criteria.search()}))
                AND (:#{#criteria.province().empty} = TRUE
                        OR s.province ILIKE :#{#criteria.province()})
                AND (:#{#criteria.commune().empty} = TRUE
                        OR s.commune ILIKE :#{#criteria.commune()})
                AND (:#{#criteria.district().empty} = TRUE
                        OR s.district ILIKE :#{#criteria.district()})
                AND (:#{#criteria.statusCodes().empty} = TRUE
                        OR s.statusCode IN :#{#criteria.statusCodes()})
        """)
    Page<StationEntity> findAll(StationCriteria criteria, Pageable pageable);

    @Query("""
        SELECT DISTINCT s.province
        FROM StationEntity s
        WHERE s.deleted = FALSE
                AND (s.statusCode = :#{T(net.pool.station.core.bootstrap.enums.EStationStatus).ACTIVE.getCode()})
                AND (:#{#province.empty} = TRUE
                        OR s.province ILIKE %:province%)
        """)
    Page<String> findAllStationProvince(String province, Pageable pageable);

    @Query("""
        SELECT DISTINCT s.commune
        FROM StationEntity s
        WHERE s.deleted = FALSE
                AND (s.statusCode = :#{T(net.pool.station.core.bootstrap.enums.EStationStatus).ACTIVE.getCode()})
                AND (:#{#commune.empty} = TRUE
                        OR s.commune ILIKE %:commune%)
        """)
    Page<String> findAllStationCommune(String commune, Pageable pageable);

    @Query("""
        SELECT DISTINCT s.district
        FROM StationEntity s
        WHERE s.deleted = FALSE
                AND (s.statusCode = :#{T(net.pool.station.core.bootstrap.enums.EStationStatus).ACTIVE.getCode()})
                AND (:#{#district.empty} = TRUE
                        OR s.district ILIKE %:district%)
        """)
    Page<String> findAllStationDistrict(String district, Pageable pageable);
}
