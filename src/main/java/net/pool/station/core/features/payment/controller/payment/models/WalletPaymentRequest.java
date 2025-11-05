package net.pool.station.core.features.payment.controller.payment.models;

public record WalletPaymentRequest(
        Long walletId,
        Integer amount,
        String currency
) {
}
