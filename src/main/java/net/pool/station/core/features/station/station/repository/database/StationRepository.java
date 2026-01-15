package net.pool.station.core.features.station.station.repository.database;

import net.pool.station.core.domain.station.StationCriteria;
import net.pool.station.core.features.station.station.repository.database.dao.StationDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
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
        SELECT DISTINCT s.stationId AS stationId
        FROM StationEntity s
        LEFT JOIN StationSpaceEntity ss ON ss.stationId = s.stationId
        LEFT JOIN GameEntity g ON g.stationSpaceId = ss.stationSpaceId
        LEFT JOIN AreaEntity a ON a.stationSpaceId = ss.stationSpaceId
        LEFT JOIN StationResourceEntity sr ON sr.areaId = a.areaId
        LEFT JOIN StationResourceSpecEntity srs ON sr.stationResourceId = srs.stationResourceId
        WHERE s.deleted = FALSE
                AND (:#{#criteria.search().empty} = TRUE
                        OR (s.stationName ILIKE %:#{#criteria.search()}%
                                OR s.statusCode = :#{#criteria.search()}))
                AND (:#{#criteria.createdBy().empty} = TRUE
                        OR s.createdBy = :#{#criteria.createdBy()})
                AND (:#{#criteria.province().empty} = TRUE
                        OR s.province ILIKE :#{#criteria.province()})
                AND (:#{#criteria.commune().empty} = TRUE
                        OR s.commune ILIKE :#{#criteria.commune()})
                AND (:#{#criteria.district().empty} = TRUE
                        OR s.district ILIKE :#{#criteria.district()})
                AND (:#{#criteria.statusCodes().empty} = TRUE
                        OR s.statusCode IN :#{#criteria.statusCodes()})
                AND (:#{#criteria.gameName().empty} = TRUE
                        OR g.gameName ILIKE %:#{#criteria.gameName()}%)
                AND (:#{#criteria.pcCpu().empty} = TRUE
                        OR srs.pcCpu = :#{#criteria.pcCpu()})
                AND (:#{#criteria.pcCpu().empty} = TRUE
                        OR srs.pcCpu LIKE %:#{#criteria.pcCpu()}%)
                AND (:#{#criteria.pcRam().empty} = TRUE
                        OR srs.pcRam LIKE %:#{#criteria.pcRam()}%)
                AND (:#{#criteria.pcGpu().empty} = TRUE
                        OR srs.pcGpu LIKE %:#{#criteria.pcGpu()}%)
                AND (:#{#criteria.pcMonitor().empty} = TRUE
                        OR srs.pcMonitor LIKE %:#{#criteria.pcMonitor()}%)
                AND (:#{#criteria.pcMonitor().empty} = TRUE
                        OR srs.pcMonitor LIKE %:#{#criteria.pcMonitor()}%)
                AND (:#{#criteria.pcKeyboard().empty} = TRUE
                        OR srs.pcKeyboard LIKE %:#{#criteria.pcKeyboard()}%)
                AND (:#{#criteria.pcMouse().empty} = TRUE
                        OR srs.pcMouse LIKE %:#{#criteria.pcMouse()}%)
                AND (:#{#criteria.pcHeadphone().empty} = TRUE
                        OR srs.pcHeadphone LIKE %:#{#criteria.pcHeadphone()}%)
                AND (:#{#criteria.btTableDetail().empty} = TRUE
                        OR srs.btTableDetail LIKE %:#{#criteria.btTableDetail()}%)
                AND (:#{#criteria.btCueDetail().empty} = TRUE
                        OR srs.btCueDetail LIKE %:#{#criteria.btCueDetail()}%)
                AND (:#{#criteria.btBallDetail().empty} = TRUE
                        OR srs.btBallDetail LIKE %:#{#criteria.btBallDetail()}%)
                AND (:#{#criteria.csConsoleModel().empty} = TRUE
                        OR srs.csConsoleModel LIKE %:#{#criteria.csConsoleModel()}%)
                AND (:#{#criteria.csTvModel().empty} = TRUE
                        OR srs.csTvModel LIKE %:#{#criteria.csTvModel()}%)
                AND (:#{#criteria.csControllerType().empty} = TRUE
                        OR srs.csControllerType LIKE %:#{#criteria.csControllerType()}%)
                AND (:#{#criteria.csControllerCount()} = 0
                        OR srs.csControllerCount = :#{#criteria.csControllerCount()})
        """)
    Page<StationDao> findAll(StationCriteria criteria, Pageable pageable);

    @Query(value = """
        SELECT 
            s AS station,
            CASE 
                    WHEN :latitude > 0.0 AND :longitude > 0.0 
                            THEN FUNCTION(
                                     'ST_DISTANCE_SPHERE',
                                     FUNCTION('POINT', s.longitude, s.latitude),
                                     FUNCTION('POINT', :longitude, :latitude)
                                   ) 
                    ELSE 0.0
                END AS distance
                
        FROM StationEntity s
        WHERE s.stationId IN :stationIds
        """
    )
    List<StationDao> findAllByStationIdIn(List<Long> stationIds,
                                          Double latitude,
                                          Double longitude,
                                          Sort sort);

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
