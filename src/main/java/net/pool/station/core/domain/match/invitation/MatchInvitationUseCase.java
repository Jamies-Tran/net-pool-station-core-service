package net.pool.station.core.domain.match.invitation;

import net.pool.station.core.domain.DomainKey;

import java.util.List;

public interface MatchInvitationUseCase {
    void saveAll(DomainKey<Long> matchMakingId, List<MatchInvitation> matchInvitations);

    void accept(DomainKey<Long> matchInvitationId);

    void deny(DomainKey<Long> matchInvitationId);
}
