package net.pool.station.core.domain.match.invitation;

import lombok.With;

public record MatchInvitation(
        Long matchInvitationId,
        @With Long matchMakingId,
        Long accountId,
        String message,
        String statusCode,
        String statusName,
        String createdBy
) {
}
