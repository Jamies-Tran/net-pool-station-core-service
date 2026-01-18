package net.pool.station.core.features.match.making.match.making.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.rest.response.MyPageResponse;
import net.pool.station.core.bootstrap.rest.response.MySorter;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.match.making.MatchMakingCriteria;
import net.pool.station.core.domain.match.making.MatchMakingUseCase;
import net.pool.station.core.features.match.making.match.making.controller.models.MatchMakingRequest;
import net.pool.station.core.features.match.making.match.making.controller.models.MatchMakingRequestMapping;
import net.pool.station.core.features.match.making.match.making.controller.models.MatchMakingResponse;
import net.pool.station.core.features.match.making.match.making.controller.models.MatchMakingResponseMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MatchMakingsController implements MatchMakingsApi {
    MatchMakingUseCase matchMakingUseCase;

    MatchMakingRequestMapping requestMapping;

    MatchMakingResponseMapping responseMapping;

    @Override
    public MyValueResponse<Long> save(MatchMakingRequest request) {
        Long matchMakingId = matchMakingUseCase.save(requestMapping.toDto(request));

        return MyValueResponse.success(matchMakingId);
    }

    @Override
    public MyPageResponse<MatchMakingResponse> findAll(
            String search,
            String createdBy,
            List<LocalDate> timeRangeStartAt,
            List<String> statusCodes,
            String sorter, Integer current, Integer pageSize
    ) {
        MatchMakingCriteria criteria = MatchMakingCriteria.builder()
                .search(search)
                .createdBy(createdBy)
                .timeRangeStartAt(timeRangeStartAt)
                .statusCodes(statusCodes)
                .build();
        PageRequest pageRequest = PageRequest.of(current, pageSize, MySorter.of(sorter));
        Page<MatchMakingResponse> responses = matchMakingUseCase.findAll(criteria, pageRequest)
                .map(responseMapping::toModel);

        return MyPageResponse.success(responses);
    }

    @Override
    public MyValueResponse<?> emptyParticipant(Long matchParticipantId) {
        matchMakingUseCase.emptyParticipant(DomainKey.of(matchParticipantId));

        return MyValueResponse.successNoData();
    }

    @Override
    public MyValueResponse<?> participantWalletPayment(Long matchParticipateId) {
        matchMakingUseCase.participantWalletPayment(DomainKey.of(matchParticipateId));

        return MyValueResponse.successNoData();
    }
}
