package net.pool.station.core.features.match.participant.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.match.participant.MatchParticipantUseCase;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MatchParticipantController implements MatchParticipantApi {
    MatchParticipantUseCase matchParticipantUseCase;

    @Override
    public MyValueResponse<?> empty(Long matchParticipantId) {
        matchParticipantUseCase.emptyFilledParticipant(DomainKey.of(matchParticipantId));

        return MyValueResponse.successNoData();
    }
}
