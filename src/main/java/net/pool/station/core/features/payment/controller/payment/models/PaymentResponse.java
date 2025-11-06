package net.pool.station.core.features.payment.controller.payment.models;

public record PaymentResponse(
        String bin,
        String accountNumber,
        String accountName,
        String description,
        String paymentLink,
        String orderCode,
        String status,
        String checkoutUrl,
        String qrCode
) {
}
