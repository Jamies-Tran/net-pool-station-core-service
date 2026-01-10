package net.pool.station.core.domain.wallet;

import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.wallet.ledger.WalletLedger;

import java.util.List;
import java.util.Optional;

public interface WalletUseCase {
    void save(DomainKey<Long> accountId, Wallet wallet);

    Optional<Wallet> findByAccountId(DomainKey<Long> accountId);

    void updateBalance(DomainKey<Long> walletId, WalletLedger walletLedger);

    void updateBalance(List<WalletLedger> walletLedgers);

    void enable(DomainKey<Long> accountId);

    void disable(DomainKey<Long> accountId);

    void enableDirectPayment();
}
