package net.pool.station.core.features.match.invitation.controller.websocket;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.rest.response.MyListResponse;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.match.invitation.MatchInvitation;
import net.pool.station.core.domain.match.invitation.MatchInvitationUseCase;
import net.pool.station.core.features.match.invitation.controller.models.MatchInvitationResponse;
import net.pool.station.core.features.match.invitation.controller.models.MatchInvitationResponseMapper;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MatchInvitationWsController {
    MatchInvitationUseCase matchInvitationUseCase;

    MatchInvitationResponseMapper responseMapper;

    @MessageMapping("/match-making/{matchMakingId}/invitations")
    @SendTo("/topic/match-making/{matchMakingId}/invitations")
    public MyListResponse<MatchInvitationResponse> findMatchInvitationByMatchMakingId(
            @DestinationVariable Long matchMakingId
    ) {
        List<MatchInvitation> invitations = matchInvitationUseCase.findAllByMatchMakingId(DomainKey.of(matchMakingId));

        return MyListResponse.success(responseMapper.toModel(invitations));
    }
}
