package net.pool.station.core.domain.transaction;

import lombok.Builder;

@Builder
public record Transaction(
        Long transactionId,
        Long bookingId,
        Long walletId,
        String transactionCode,
        Integer amount,
        String currency,
        String paymentTypeCode,
        String paymentTypeName,
        String paymentMethodCode,
        String paymentMethodName,
        String statusCode,
        String statusName
) {
}
