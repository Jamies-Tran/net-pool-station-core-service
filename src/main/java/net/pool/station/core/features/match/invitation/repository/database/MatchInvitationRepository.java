package net.pool.station.core.features.match.invitation.repository.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MatchInvitationRepository extends JpaRepository<MatchInvitationEntity, Long> {
    Optional<MatchInvitationEntity> findByMatchInvitationIdAndDeletedFalse(Long matchInvitationId);
}
