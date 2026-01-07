package net.pool.station.core.features.match.invitation.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.match.invitation.MatchInvitation;
import net.pool.station.core.domain.match.invitation.MatchInvitationUseCase;
import net.pool.station.core.features.match.invitation.controller.models.MatchInvitationListRequest;
import net.pool.station.core.features.match.invitation.controller.models.MatchInvitationRequestMapper;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MatchInvitationsController implements MatchInvitationsApi {
    MatchInvitationUseCase matchInvitationUseCase;

    MatchInvitationRequestMapper requestMapper;

    @Override
    public MyValueResponse<?> saveAll(Long matchMakingId, MatchInvitationListRequest request) {
        List<MatchInvitation> matchInvitations = requestMapper.toDto(request.invitations());
        matchInvitationUseCase.saveAll(DomainKey.of(matchMakingId), matchInvitations);

        return MyValueResponse.successNoData();
    }
}
