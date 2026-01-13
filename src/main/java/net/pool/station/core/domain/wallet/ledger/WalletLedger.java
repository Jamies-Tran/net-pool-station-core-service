package net.pool.station.core.domain.wallet.ledger;

import lombok.Builder;
import lombok.With;
import net.pool.station.core.domain.transaction.Transaction;

@Builder
public record WalletLedger(
        Long walletLedgerId,
        Long walletId,
        Long transactionId,
        @With Integer currentBalance,
        Integer changeAmount,
        @With Integer newBalance,
        @With Integer chargedCommission,
        @With Integer newContributedCommission,
        @With Transaction transaction
        ) {
}
