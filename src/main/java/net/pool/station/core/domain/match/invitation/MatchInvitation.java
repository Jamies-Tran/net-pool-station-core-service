package net.pool.station.core.domain.match.invitation;

import lombok.With;
import net.pool.station.core.domain.account.Account;

public record MatchInvitation(
        Long matchInvitationId,
        @With Long matchMakingId,
        Long accountId,
        @With Account accountHost,
        String message,
        String statusCode,
        String statusName,
        String createdBy
) {
}
