package net.pool.station.core.features.match.invitation.controller.models;

public record MatchInvitationRequest(
        Long accountId,
        String message
) {
}
