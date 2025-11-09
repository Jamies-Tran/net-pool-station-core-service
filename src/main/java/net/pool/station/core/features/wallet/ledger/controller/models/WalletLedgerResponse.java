package net.pool.station.core.features.wallet.ledger.controller.models;

public record WalletLedgerResponse(
        Long walletLedgerId,
        Long walletId,
        Long transactionId,
        Integer currentBalance,
        Integer changeAmount,
        Integer newBalance
) {
}
