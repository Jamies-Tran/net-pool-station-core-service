package net.pool.station.core.features.match.making.match.making.controller.models.participant;

import lombok.With;
import net.pool.station.core.features.account.account.controller.models.AccountResponse;

public record MatchParticipantResponse(
        Long matchParticipantId,
        Long accountId,
        Long matchMakingId,
        String typeCode,
        String typeName,
        String paymentMethodCode,
        String paymentMethodName,
        String readyStatusCode,
        String readyStatusName,
        Integer shareAmount,
        String statusCode,
        String statusName,
        AccountResponse account
) {
}
