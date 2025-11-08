package net.pool.station.core.domain.wallet.ledger;

import lombok.With;

public record WalletLedger(
        Long walletLedgerId,
        Long walletId,
        Long transactionId,
        @With Integer currentBalance,
        Integer changeAmount,
        Integer newBalance
) {
    public WalletLedger {
        newBalance = currentBalance + changeAmount;
    }
}
