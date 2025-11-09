package net.pool.station.core.features.transaction.controller.models;

import java.time.LocalDateTime;

public record TransactionResponse(
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
        LocalDateTime paymentCompleteAt,
        String statusCode,
        String statusName
) {
}
