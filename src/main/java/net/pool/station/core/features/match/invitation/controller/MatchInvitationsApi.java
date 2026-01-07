package net.pool.station.core.features.match.invitation.controller;

import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.features.match.invitation.controller.models.MatchInvitationListRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/api/match-invitation")
public interface MatchInvitationsApi {
    @PostMapping("/{matchMakingId}")
    @PreAuthorize("hasRole('ROLE_PLAYER')")
    MyValueResponse<?> saveAll(@PathVariable Long matchMakingId,
                               @RequestBody MatchInvitationListRequest request);
}
