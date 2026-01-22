package net.pool.station.core.domain.transaction;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record Transaction(
        Long transactionId,
        Long matchMakingId,
        Long bookingId,
        Long walletId,
        Long matchParticipantId,
        String transactionCode,
        Integer amount,
        String currency,
        String paymentTypeCode,
        String paymentTypeName,
        String paymentMethodCode,
        String paymentMethodName,
        LocalDateTime paymentCompleteAt,
        String statusCode,
        String statusName
) {
}
