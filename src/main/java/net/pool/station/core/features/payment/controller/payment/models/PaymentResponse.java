package net.pool.station.core.features.payment.controller.payment.models;

public record PaymentResponse(
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
