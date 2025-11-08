package net.pool.station.core.domain.wallet.ledger;

import net.pool.station.core.domain.DomainKey;

import java.util.List;
import java.util.Optional;

public interface WalletLedgerUseCase {
    void save(WalletLedger walletLedger);

    List<WalletLedger> findAllByWalletId(Long walletId);

    Optional<WalletLedger> findById(DomainKey<Long> walletLedgerId);
}
