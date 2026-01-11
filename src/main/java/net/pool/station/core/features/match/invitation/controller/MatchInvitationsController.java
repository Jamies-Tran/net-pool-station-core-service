package net.pool.station.core.features.match.invitation.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.rest.response.MyPageResponse;
import net.pool.station.core.bootstrap.rest.response.MySorter;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.match.invitation.MatchInvitation;
import net.pool.station.core.domain.match.invitation.MatchInvitationCriteria;
import net.pool.station.core.domain.match.invitation.MatchInvitationUseCase;
import net.pool.station.core.features.match.invitation.controller.models.MatchInvitationListRequest;
import net.pool.station.core.features.match.invitation.controller.models.MatchInvitationRequestMapper;
import net.pool.station.core.features.match.invitation.controller.models.MatchInvitationResponse;
import net.pool.station.core.features.match.invitation.controller.models.MatchInvitationResponseMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MatchInvitationsController implements MatchInvitationsApi {
    MatchInvitationUseCase matchInvitationUseCase;

    MatchInvitationRequestMapper requestMapper;

    MatchInvitationResponseMapper responseMapper;

    @Override
    public MyValueResponse<?> saveAll(Long matchMakingId, MatchInvitationListRequest request) {
        List<MatchInvitation> matchInvitations = requestMapper.toDto(request.invitations());
        matchInvitationUseCase.saveAll(DomainKey.of(matchMakingId), matchInvitations);

        return MyValueResponse.successNoData();
    }

    @Override
    public MyPageResponse<MatchInvitationResponse> findAll(
            Long matchMakingId,
            Long accountId,
            List<LocalDateTime> timeRange,
            List<String> statusCodes,
            String sorter, Integer current, Integer pageSize
    ) {
        MatchInvitationCriteria criteria = MatchInvitationCriteria.builder()
                .matchMakingId(matchMakingId)
                .accountId(accountId)
                .statusCodes(statusCodes)
                .timeRange(timeRange)
                .build();
        PageRequest pageRequest = PageRequest.of(current, pageSize, MySorter.of(sorter));
        Page<MatchInvitationResponse> responses = matchInvitationUseCase.findAll(criteria, pageRequest)
                .map(responseMapper::toModel);

        return MyPageResponse.success(responses);
    }
}
