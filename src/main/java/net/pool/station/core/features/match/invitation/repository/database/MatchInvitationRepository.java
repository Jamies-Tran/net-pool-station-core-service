package net.pool.station.core.features.match.invitation.repository.database;

import net.pool.station.core.domain.match.invitation.MatchInvitation;
import net.pool.station.core.domain.match.invitation.MatchInvitationCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatchInvitationRepository extends JpaRepository<MatchInvitationEntity, Long> {
    Optional<MatchInvitationEntity> findByMatchInvitationIdAndDeletedFalse(Long matchInvitationId);

    @Query("""
        SELECT mi
        FROM MatchInvitationEntity mi
        WHERE (:#{#criteria.accountId()} = 0
                OR mi.accountId = :#{#criteria.accountId()})
        AND (:#{#criteria.matchMakingId()} = 0
                OR mi.matchMakingId = :#{#criteria.matchMakingId()})
        AND (:#{#criteria.timeRange().empty} = TRUE
                OR mi.createdAt BETWEEN :#{#criteria.from()} AND :#{#criteria.to()})
        AND (:#{#criteria.statusCodes().empty} = TRUE
                OR mi.statusCode IN :#{#criteria.statusCodes()})
        """)
    Page<MatchInvitationEntity> findAll(MatchInvitationCriteria criteria, Pageable pageable);

    @Query("""
        SELECT COUNT(m) > 0
        FROM MatchMakingEntity m
        WHERE m.statusCode = 'PENDING' AND m.matchMakingId = :matchMakingId
        """)
    Boolean allowInvitationByMatchMakingId(Long matchMakingId);

    @Query("""
        SELECT COUNT(mi) > 0
        FROM MatchInvitationEntity mi
        INNER JOIN MatchMakingEntity m ON mi.matchMakingId = m.matchMakingId
        WHERE m.matchMakingId = :matchMakingId AND m.createdBy = :createdBy
        """)
    Boolean allowInvitationByMatchMakingIdAndMatchMakingCreatedBy(Long matchMakingId, String createdBy);

    List<MatchInvitationEntity> findAllByMatchMakingId(Long matchMakingId);
}
