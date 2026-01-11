package net.pool.station.core.domain.match.invitation;

import net.pool.station.core.domain.DomainKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface MatchInvitationUseCase {
    void saveAll(DomainKey<Long> matchMakingId, List<MatchInvitation> matchInvitations);

    void accept(DomainKey<Long> matchInvitationId);

    void deny(DomainKey<Long> matchInvitationId);

    Page<MatchInvitation> findAll(MatchInvitationCriteria criteria, PageRequest pageRequest);
}
