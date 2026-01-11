package net.pool.station.core.features.match.invitation.controller.models;

import net.pool.station.core.features.account.account.controller.models.AccountResponse;

public record MatchInvitationResponse(
        Long matchInvitationId,
        Long matchMakingId,
        Long accountId,
        String message,
        String statusCode,
        String statusName,
        AccountResponse accountHost
) {
}
