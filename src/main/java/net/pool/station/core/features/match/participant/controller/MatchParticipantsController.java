package net.pool.station.core.features.match.participant.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.rest.response.MyPageResponse;
import net.pool.station.core.bootstrap.rest.response.MySorter;
import net.pool.station.core.domain.match.participant.MatchParticipantCriteria;
import net.pool.station.core.domain.match.participant.MatchParticipantUseCase;
import net.pool.station.core.features.match.making.match.making.controller.models.participant.MatchParticipantResponse;
import net.pool.station.core.features.match.making.match.making.controller.models.participant.MatchParticipantResponseMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MatchParticipantsController implements MatchParticipantsApi {
    MatchParticipantUseCase matchParticipantUseCase;

    MatchParticipantResponseMapper responseMapper;

    @Override
    public MyPageResponse<MatchParticipantResponse> findAll(
            String search,
            Long matchMakingId,
            List<String> typeCodes,
            List<String> statusCodes,
            String sorter,
            Integer current,
            Integer pageSize
    ) {
        MatchParticipantCriteria criteria = MatchParticipantCriteria.builder()
                .search(search)
                .matchMakingId(matchMakingId)
                .typeCodes(typeCodes)
                .statusCodes(statusCodes)
                .build();
        PageRequest pageRequest = PageRequest.of(current, pageSize, MySorter.of(sorter));
        Page<MatchParticipantResponse> responses = matchParticipantUseCase.findAll(criteria, pageRequest)
                .map(responseMapper::toModel);

        return MyPageResponse.success(responses);
    }
}
