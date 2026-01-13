package net.pool.station.core.features.match.joining.controller.models;

import net.pool.station.core.features.account.account.controller.models.AccountResponse;

public record MatchJoiningRegistrationResponse(
        Long matchJoiningRegistrationId,
        Long matchMakingId,
        String message,
        String statusCode,
        String statusName,
        AccountResponse account
) {
}
