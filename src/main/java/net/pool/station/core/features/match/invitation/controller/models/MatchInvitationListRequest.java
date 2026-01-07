package net.pool.station.core.features.match.invitation.controller.models;

import java.util.List;

public record MatchInvitationListRequest(
        List<MatchInvitationRequest> invitations
) {
}
