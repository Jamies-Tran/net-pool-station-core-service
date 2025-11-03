package net.pool.station.core.features.wallet.controller.models;

public record WalletResponse(
        Long walletId,
        Long accountId,
        Double balance,
        Boolean directPayment,
        String statusCode,
        String statusName
) {
}
