package net.pool.station.core.features.match.invitation.controller;

import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/api/match-invitation/{matchInvitationId}")
public interface MatchInvitationApi {
    @PatchMapping("/accept")
    @PreAuthorize("hasRole('ROLE_PLAYER')")
    MyValueResponse<?> accept(@PathVariable Long matchInvitationId);

    @PatchMapping("/deny")
    @PreAuthorize("hasRole('ROLE_PLAYER')")
    MyValueResponse<?> deny(@PathVariable Long matchInvitationId);
}
