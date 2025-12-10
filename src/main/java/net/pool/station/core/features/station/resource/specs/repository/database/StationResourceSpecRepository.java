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
                CASE
                    WHEN :#{#criteria.typeCode()} = "PC_CPU" THEN srs.pcCpu
                    WHEN :#{#criteria.typeCode()} = "PC_RAM" THEN srs.pcRam
                    WHEN :#{#criteria.typeCode()} = "PC_GPU" THEN srs.pcGpu
                    WHEN :#{#criteria.typeCode()} = "PC_MONITOR" THEN srs.pcMonitor
                    WHEN :#{#criteria.typeCode()} = "PC_KEYBOARD" THEN srs.pcKeyboard
                    WHEN :#{#criteria.typeCode()} = "PC_MOUSE" THEN srs.pcMouse
                    WHEN :#{#criteria.typeCode()} = "PC_HEADPHONE" THEN srs.pcHeadphone
                    WHEN :#{#criteria.typeCode()} = "BT_TABLE_DETAIL" THEN srs.btTableDetail
                    WHEN :#{#criteria.typeCode()} = "BT_CUE_DETAIL" THEN srs.btCueDetail
                    WHEN :#{#criteria.typeCode()} = "BT_BALL_DETAIL" THEN srs.btBallDetail
                    WHEN :#{#criteria.typeCode()} = "CS_CONSOLE_MODEL" THEN srs.csConsoleModel
                    WHEN :#{#criteria.typeCode()} = "CS_TV_MODEL" THEN srs.csTvModel
                    WHEN :#{#criteria.typeCode()} = "CS_CONTROLLER_TYPE" THEN srs.csControllerType
                    WHEN :#{#criteria.typeCode()} = "CS_CONTROLLER_COUNT" THEN srs.csControllerCount
                END
        FROM StationResourceSpecEntity srs
        WHERE srs.deleted = FALSE
            AND (:#{#criteria.pcCpu().empty} = TRUE
                OR srs.pcCpu ILIKE %:#{#criteria.pcCpu()}%)
            AND (:#{#criteria.pcRam().empty} = TRUE
                OR srs.pcRam ILIKE %:#{#criteria.pcRam()}%)
            AND (:#{#criteria.pcGpu().empty} = TRUE 
                OR srs.pcGpu ILIKE %:#{#criteria.pcGpu()}%)
            AND (:#{#criteria.pcMonitor().empty} = TRUE 
                OR srs.pcMonitor ILIKE %:#{#criteria.pcMonitor()}%)
            AND (:#{#criteria.pcKeyboard().empty} = TRUE 
                OR srs.pcKeyboard ILIKE %:#{#criteria.pcKeyboard()}%)
            AND (:#{#criteria.pcMouse().empty} = TRUE 
                OR srs.pcMouse ILIKE %:#{#criteria.pcMouse()}%)
            AND (:#{#criteria.pcHeadphone().empty} = TRUE 
                OR srs.pcHeadphone ILIKE %:#{#criteria.pcHeadphone()}%)
            AND (:#{#criteria.btTableDetail().empty} = TRUE 
                OR srs.btTableDetail ILIKE %:#{#criteria.btTableDetail()}%)
            AND (:#{#criteria.btCueDetail().empty} = TRUE 
                OR srs.btCueDetail ILIKE %:#{#criteria.btCueDetail()}%)
            AND (:#{#criteria.btBallDetail().empty} = TRUE 
                OR srs.btBallDetail ILIKE %:#{#criteria.btBallDetail()}%)
            AND (:#{#criteria.csConsoleModel().empty} = TRUE 
                OR srs.csConsoleModel ILIKE %:#{#criteria.csConsoleModel()}%)
            AND (:#{#criteria.csTvModel().empty} = TRUE
                OR srs.csTvModel ILIKE %:#{#criteria.csTvModel()}%)
            AND (:#{#criteria.csControllerType().empty} = TRUE 
                OR srs.csControllerType ILIKE %:#{#criteria.csControllerType()}%)
            AND (:#{#criteria.csControllerCount()} = 0 
                OR srs.csControllerCount = :#{#criteria.csControllerCount()})
        """)
    List<String> findAll(StationResourceSpecCriteria criteria);

    @Query("""
        SELECT sr.typeCode
        FROM StationResourceEntity sr
        WHERE sr.stationResourceId = :stationResourceId
        """)
    String findStationResourceTypeById(Long stationResourceId);
}
