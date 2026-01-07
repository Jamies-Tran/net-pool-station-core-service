package net.pool.station.core.features.match.invitation.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.match.invitation.MatchInvitationUseCase;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MatchInvitationController implements MatchInvitationApi {
    MatchInvitationUseCase matchInvitationUseCase;

    @Override
    public MyValueResponse<?> accept(Long matchInvitationId) {
        matchInvitationUseCase.accept(DomainKey.of(matchInvitationId));

        return MyValueResponse.successNoData();
    }

    @Override
    public MyValueResponse<?> deny(Long matchInvitationId) {
        matchInvitationUseCase.deny(DomainKey.of(matchInvitationId));

        return MyValueResponse.successNoData();
    }
}
