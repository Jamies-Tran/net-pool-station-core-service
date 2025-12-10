package net.pool.station.core.features.station.resource.specs.repository.database;

import net.pool.station.core.domain.station.resource.specs.StationResourceSpecCriteria;
import net.pool.station.core.features.station.resource.specs.repository.database.dao.StationResourceSpecDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StationResourceSpecRepository extends JpaRepository<StationResourceSpecEntity, Long> {
    Optional<StationResourceSpecEntity> findByStationResourceIdAndDeletedFalse(Long stationResourceId);

    Optional<StationResourceSpecEntity> findByStationResourceSpecIdAndDeletedFalse(Long stationResourceSpecId);

    @Query("""
        SELECT DISTINCT
                srs.pcCpu AS pcCpu,
                srs.pcRam AS pcRam,
                srs.pcGpu AS pcGpu,
                srs.pcMonitor AS pcMonitor,
                srs.pcKeyboard AS pcKeyboard,
                srs.pcMouse AS pcMouse,
                srs.pcHeadphone AS pcHeadphone,
                srs.btTableDetail AS btTableDetail,
                srs.btCueDetail AS btCueDetail,
                srs.btBallDetail AS btBallDetail,
                srs.csConsoleModel AS csConsoleModel,
                srs.csTvModel AS csTvModel,
                srs.csControllerType AS csControllerType,
                srs.csControllerCount AS csControllerCount,
                srs.typeCode AS typeCode,
                srs.typeName AS typeName
        FROM StationResourceSpecEntity srs
        WHERE srs.deleted = FALSE
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
    List<StationResourceSpecDao> findAll(StationResourceSpecCriteria criteria);
}
