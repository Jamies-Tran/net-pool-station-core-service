package net.pool.station.core.domain.match.joining;

import net.pool.station.core.domain.account.Account;

import java.time.LocalDateTime;

public record MatchJoiningRegistration(
        Long matchJoiningRegistrationId,
        Long matchMakingId,
        String message,
        String statusCode,
        String statusName,
        String createdBy,
        LocalDateTime createdAt,
        Account account
) {
}
