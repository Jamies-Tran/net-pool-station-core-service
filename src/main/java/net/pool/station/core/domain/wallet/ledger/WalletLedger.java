package net.pool.station.core.domain.wallet.ledger;

import lombok.Builder;
import lombok.With;

@Builder
public record WalletLedger(
        Long walletLedgerId,
        Long walletId,
        Long transactionId,
        @With Integer currentBalance,
        Integer changeAmount,
        @With Integer newBalance
) {
}
