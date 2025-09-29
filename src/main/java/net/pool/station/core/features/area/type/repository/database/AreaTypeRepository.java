package net.pool.station.core.features.area.type.repository.database;

import net.pool.station.core.domain.area.type.AreaTypeCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AreaTypeRepository extends JpaRepository<AreaTypeEntity, Long> {
    Boolean existsByTypeCode(String typeCode);

    Boolean existsByTypeName(String typeName);

    Optional<AreaTypeEntity> findByAreaTypeIdAndDeletedFalse(Long areaTypeId);

    @Query("""
        SELECT at
        FROM AreaTypeEntity at
        WHERE at.deleted = FALSE
                AND (:#{#criteria.search().empty} = TRUE
                        OR at.typeName ILIKE %:#{#criteria.search()}%)
                AND (:#{#criteria.typeCodes().empty} = TRUE
                        OR at.typeCode IN :#{#criteria.typeCodes()})
                AND (:#{#criteria.statusCodes().empty} = TRUE
                        OR at.statusCode IN :#{#criteria.statusCodes()})
        """)
    Page<AreaTypeEntity> findAll(AreaTypeCriteria criteria, Pageable pageable);
}
