package net.pool.station.core.features.payment.controller.payment.models;

public record BookingPaymentRequest(
        Long bookingId,
        String transactionCode,
        Integer amount,
        String currency,
        String paymentMethodCode,
        String paymentMethodName
) {
}
