package net.pool.station.core.features.match.participant.controller.websocket;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.rest.response.MyListResponse;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.match.participant.MatchParticipant;
import net.pool.station.core.domain.match.participant.MatchParticipantUseCase;
import net.pool.station.core.features.match.making.match.making.controller.models.participant.MatchParticipantResponse;
import net.pool.station.core.features.match.making.match.making.controller.models.participant.MatchParticipantResponseMapper;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MatchParticipantWsController {
    MatchParticipantUseCase matchParticipantUseCase;

    MatchParticipantResponseMapper responseMapper;

    @MessageMapping("/match-making/{matchMakingId}/participants")
    @SendTo("/topic/match-making/{matchMakingId}/participants")
    public MyListResponse<MatchParticipantResponse> findAllByMatchMakingId(
            @DestinationVariable Long matchMakingId
    ) {
        List<MatchParticipant> participants = matchParticipantUseCase.findAllByMatchMakingId(DomainKey.of(matchMakingId));

        return MyListResponse.success(responseMapper.toModel(participants));
    }
}
