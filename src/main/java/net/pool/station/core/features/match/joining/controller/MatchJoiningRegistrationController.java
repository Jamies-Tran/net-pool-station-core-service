package net.pool.station.core.features.match.joining.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotFoundException;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.match.joining.MatchJoiningRegistrationUseCase;
import net.pool.station.core.features.match.joining.controller.models.MatchJoiningRegistrationRequest;
import net.pool.station.core.features.match.joining.controller.models.MatchJoiningRegistrationRequestMapper;
import net.pool.station.core.features.match.joining.controller.models.MatchJoiningRegistrationResponse;
import net.pool.station.core.features.match.joining.controller.models.MatchJoiningRegistrationResponseMapper;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MatchJoiningRegistrationController implements MatchJoiningRegistrationApi {
    MatchJoiningRegistrationUseCase matchJoiningRegistrationUseCase;

    MatchJoiningRegistrationRequestMapper requestMapper;

    MatchJoiningRegistrationResponseMapper responseMapper;

    @Override
    public MyValueResponse<MatchJoiningRegistrationResponse> findById(Long matchJoiningRegistrationId) {
        MatchJoiningRegistrationResponse response = matchJoiningRegistrationUseCase
                .findById(DomainKey.of(matchJoiningRegistrationId))
                .map(responseMapper::toModel)
                .orElseThrow(MyResourceNotFoundException::new);

        return MyValueResponse.success(response);
    }

    @Override
    public MyValueResponse<?> update(Long matchJoiningRegistrationId, MatchJoiningRegistrationRequest request) {
        matchJoiningRegistrationUseCase.update(DomainKey.of(matchJoiningRegistrationId), requestMapper.toDto(request));

        return MyValueResponse.successNoData();
    }

    @Override
    public MyValueResponse<?> accept(Long matchJoiningRegistrationId) {
        matchJoiningRegistrationUseCase.accept(DomainKey.of(matchJoiningRegistrationId));

        return MyValueResponse.successNoData();
    }

    @Override
    public MyValueResponse<?> deny(Long matchJoiningRegistrationId) {
        matchJoiningRegistrationUseCase.deny(DomainKey.of(matchJoiningRegistrationId));

        return MyValueResponse.successNoData();
    }

    @Override
    public MyValueResponse<?> cancel(Long matchJoiningRegistrationId) {
        matchJoiningRegistrationUseCase.cancel(DomainKey.of(matchJoiningRegistrationId));

        return MyValueResponse.successNoData();
    }
}
