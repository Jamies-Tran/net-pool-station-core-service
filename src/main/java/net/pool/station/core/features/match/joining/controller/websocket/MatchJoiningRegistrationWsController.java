package net.pool.station.core.features.match.joining.controller.websocket;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.rest.response.MyListResponse;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.match.joining.MatchJoiningRegistration;
import net.pool.station.core.domain.match.joining.MatchJoiningRegistrationUseCase;
import net.pool.station.core.features.match.joining.controller.models.MatchJoiningRegistrationResponse;
import net.pool.station.core.features.match.joining.controller.models.MatchJoiningRegistrationResponseMapper;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MatchJoiningRegistrationWsController {
    MatchJoiningRegistrationUseCase matchJoiningRegistrationUseCase;

    MatchJoiningRegistrationResponseMapper responseMapper;

    @MessageMapping("/match-making/{matchMakingId}/match-joining")
    @SendTo("/topic/match-making/{matchMakingId}/match-joining")
    public MyListResponse<MatchJoiningRegistrationResponse> findAllByMatchMakingId(
            @DestinationVariable Long matchMakingId
    ) {
        List<MatchJoiningRegistration> registrations = matchJoiningRegistrationUseCase
                .findAllByMatchMakingId(DomainKey.of(matchMakingId));

        return MyListResponse.success(responseMapper.toModel(registrations));
    }
}
