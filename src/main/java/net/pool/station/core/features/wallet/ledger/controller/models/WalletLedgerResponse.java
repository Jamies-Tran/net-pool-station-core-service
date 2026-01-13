package net.pool.station.core.features.wallet.ledger.controller.models;

import net.pool.station.core.features.transaction.controller.models.TransactionResponse;

public record WalletLedgerResponse(
        Long walletLedgerId,
        Long walletId,
        Long transactionId,
        Integer currentBalance,
        Integer changeAmount,
        Integer newBalance,
        TransactionResponse transaction
) {
}
