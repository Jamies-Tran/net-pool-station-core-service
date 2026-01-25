package net.pool.station.core.features.match.joining.repository.database;

import net.pool.station.core.domain.match.joining.MatchJoiningRegistrationCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    Boolean existsByMatchJoiningRegistrationIdAndCreatedBy(Long matchJoiningRegistrationId, String accountId);

    @Query("""
        SELECT COUNT(mj) > 0
        FROM MatchJoiningRegistrationEntity mj
        INNER JOIN MatchMakingEntity m ON mj.matchMakingId = m.matchMakingId
        WHERE mj.matchJoiningRegistrationId = :matchJoiningRegistrationId
                AND m.createdBy = :accountId
        """)
    Boolean existsByMatchJoningRegistrationIdMatchMakingCreatedBy(Long matchJoiningRegistrationId, String accountId);

    @Query("""
        SELECT COUNT(m) > 0
        FROM MatchMakingEntity m
        WHERE m.statusCode = 'PENDING' AND m.matchMakingId = :matchMakingId
        """)
    Boolean allowJoiningByMatchMakingId(Long matchMakingId);

    List<MatchJoiningRegistrationEntity> findAllByMatchMakingId(Long matchMakingId);
}
