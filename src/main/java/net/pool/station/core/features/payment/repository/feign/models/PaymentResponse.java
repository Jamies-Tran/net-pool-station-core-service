package net.pool.station.core.features.payment.repository.feign.models;

public record PaymentResponse(
        String bin,
        String accountNumber,
        String accountName,
        String currency,
        String paymentLink,
        Integer amount,
        String description,
        String orderCode,
        String status,
        String checkoutUrl,
        String qrCode
) {
}
