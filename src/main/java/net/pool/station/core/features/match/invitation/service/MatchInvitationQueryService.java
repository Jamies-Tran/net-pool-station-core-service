package net.pool.station.core.features.match.invitation.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.match.invitation.MatchInvitation;
import net.pool.station.core.domain.match.invitation.MatchInvitationCriteria;
import net.pool.station.core.features.match.invitation.repository.database.MatchInvitationMapper;
import net.pool.station.core.features.match.invitation.repository.database.MatchInvitationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MatchInvitationQueryService {
    MatchInvitationRepository repository;

    MatchInvitationMapper mapper;

    protected Page<MatchInvitation> findAll(MatchInvitationCriteria criteria, PageRequest pageRequest) {
        return repository.findAll(criteria, pageRequest)
                .map(mapper::toDto);
    }

    protected Boolean allowInvitationByMatchMakingId(Long matchMakingId) {
        return repository.allowInvitationByMatchMakingId(matchMakingId);
    }

    protected Boolean allowInvitationByMatchMakingIdAndMatchMakingCreatedBy(
            Long matchMakingId,
            String createdBy
    ) {
       return repository.allowInvitationByMatchMakingIdAndMatchMakingCreatedBy(matchMakingId, createdBy);
    }
}
