package net.pool.station.core.features.match.invitation.controller;

import net.pool.station.core.bootstrap.rest.response.MyPageResponse;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.features.match.invitation.controller.models.MatchInvitationListRequest;
import net.pool.station.core.features.match.invitation.controller.models.MatchInvitationResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/v1/api/match-invitation")
public interface MatchInvitationsApi {
    @PostMapping("/{matchMakingId}")
    @PreAuthorize("hasRole('ROLE_PLAYER')")
    MyValueResponse<?> saveAll(@PathVariable Long matchMakingId,
                               @RequestBody MatchInvitationListRequest request);

    @GetMapping
    MyPageResponse<MatchInvitationResponse> findAll(
            @RequestParam(required = false, value = "matchMakingId", defaultValue = "")
            Long matchMakingId,

            @RequestParam(required = false, value = "accountId", defaultValue = "")
            Long accountId,

            @RequestParam(required = false, value = "timeRange", defaultValue = "")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            List<LocalDateTime> timeRange,

            @RequestParam(required = false, value = "statusCodes", defaultValue = "")
            List<String> statusCodes,

            @RequestParam(required = false, value = "sorter", defaultValue = "createdAt_desc")
            String sorter,

            @RequestParam(required = false, value = "current", defaultValue = "0")
            Integer current,

            @RequestParam(required = false, value = "pageSize", defaultValue = "25")
            Integer pageSize
    );
}
