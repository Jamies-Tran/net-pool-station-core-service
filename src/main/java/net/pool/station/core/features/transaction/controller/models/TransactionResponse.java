package net.pool.station.core.features.transaction.controller.models;

import net.pool.station.core.features.account.account.controller.models.AccountResponse;

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
        String statusName,
        AccountResponse account
) {
}
