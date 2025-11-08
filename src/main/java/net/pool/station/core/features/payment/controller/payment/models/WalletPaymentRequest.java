package net.pool.station.core.features.payment.controller.payment.models;

public record WalletPaymentRequest(
        Integer amount,
        String currency
) {
}
