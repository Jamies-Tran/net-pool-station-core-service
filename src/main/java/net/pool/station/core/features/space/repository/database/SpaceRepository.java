package net.pool.station.core.features.space.repository.database;

import net.pool.station.core.domain.space.SpaceCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpaceRepository extends JpaRepository<SpaceEntity, Long> {
    Boolean existsByTypeCodeAndDeletedFalse(String typeCode);

    Boolean existsByTypeNameAndDeletedFalse(String typeName);

    @Query("""
        SELECT s
        FROM SpaceEntity s
        WHERE s.deleted = FALSE
                AND s.spaceId = :spaceId
        """)
    Optional<SpaceEntity> findBySpaceId(Long spaceId);

    @Query("""
        SELECT s
        FROM SpaceEntity s
        WHERE s.deleted = FALSE
                AND (:#{#criteria.search().empty} = TRUE
                        OR (s.typeName ILIKE %:#{#criteria.search()}%
                                OR s.typeCode = :#{#criteria.search()}))
                AND (:#{#criteria.statusCodes().empty} = TRUE
                        OR s.statusCode IN :#{#criteria.statusCodes()})
        """)
    Page<SpaceEntity> findAll(SpaceCriteria criteria, Pageable pageable);
}
