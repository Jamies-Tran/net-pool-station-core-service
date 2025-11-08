package net.pool.station.core.domain.payment;

import lombok.Builder;

@Builder
public record Payment(
        Long walletId,
        Long bookingId,
        Integer amount,
        String currency,
        String paymentTypeCode,
        String paymentTypeName,
        String paymentMethodCode,
        String paymentMethodName,

        String bin,
        String accountNumber,
        String accountName,
        String description,
        String paymentLinkId,
        String orderCode,
        String status,
        String checkoutUrl,
        String qrCode
) {
}
