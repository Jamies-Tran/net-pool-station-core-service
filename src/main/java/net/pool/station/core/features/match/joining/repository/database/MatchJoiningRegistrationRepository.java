package net.pool.station.core.features.match.joining.repository.database;

import net.pool.station.core.domain.match.joining.MatchJoiningRegistrationCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchJoiningRegistrationRepository extends JpaRepository<MatchJoiningRegistrationEntity, Long> {
    @Query("""
        SELECT mj
        FROM MatchJoiningRegistrationEntity mj
        WHERE (:#{#criteria.matchMakingId()} = 0
                OR mj.matchMakingId = :#{#criteria.matchMakingId()})
        AND (:#{#criteria.createdBy().empty} = TRUE
                OR mj.createdBy = :#{#criteria.createdBy()})
        AND (:#{#criteria.statusCodes().empty} = TRUE
                OR mj.statusCode IN :#{#criteria.statusCodes()})
        AND (mj.createdAt BETWEEN :#{#criteria.timeRange().get(0)} AND :#{#criteria.timeRange().get(1)})
        """)
    Page<MatchJoiningRegistrationEntity> findAll(MatchJoiningRegistrationCriteria criteria, Pageable pageable);
}
