package net.pool.station.core.domain.payment;

public record Payment(
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
        String paymentLink,
        String orderCode,
        String status,
        String checkoutUrl,
        String qrCode
) {
}
