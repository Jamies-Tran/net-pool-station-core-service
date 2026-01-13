package net.pool.station.core.features.match.joining.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.rest.response.MyPageResponse;
import net.pool.station.core.bootstrap.rest.response.MySorter;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.domain.match.joining.MatchJoiningRegistrationCriteria;
import net.pool.station.core.domain.match.joining.MatchJoiningRegistrationUseCase;
import net.pool.station.core.features.match.joining.controller.models.MatchJoiningRegistrationRequest;
import net.pool.station.core.features.match.joining.controller.models.MatchJoiningRegistrationRequestMapper;
import net.pool.station.core.features.match.joining.controller.models.MatchJoiningRegistrationResponse;
import net.pool.station.core.features.match.joining.controller.models.MatchJoiningRegistrationResponseMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MatchJoiningRegistrationsController implements MatchJoiningRegistrationsApi {
    MatchJoiningRegistrationUseCase matchJoiningRegistrationUseCase;

    MatchJoiningRegistrationRequestMapper requestMapper;

    MatchJoiningRegistrationResponseMapper responseMapper;

    @Override
    public MyValueResponse<?> save(MatchJoiningRegistrationRequest request) {
        matchJoiningRegistrationUseCase.save(requestMapper.toDto(request));

        return MyValueResponse.successNoData();
    }

    @Override
    public MyPageResponse<MatchJoiningRegistrationResponse> findAll(
            Long matchMakingId,
            String createdBy,
            List<LocalDateTime> timeRange,
            List<String> statusCodes,
            String sorter,
            Integer current,
            Integer pageSize
    ) {
        MatchJoiningRegistrationCriteria criteria = MatchJoiningRegistrationCriteria.builder()
                .matchMakingId(matchMakingId)
                .createdBy(createdBy)
                .timeRange(timeRange)
                .statusCodes(statusCodes)
                .build();
        PageRequest pageRequest = PageRequest.of(current, pageSize, MySorter.of(sorter));
        Page<MatchJoiningRegistrationResponse> responses = matchJoiningRegistrationUseCase.findAll(criteria, pageRequest)
                .map(responseMapper::toModel);

        return MyPageResponse.success(responses);
    }
}
