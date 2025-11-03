package net.pool.station.core.domain.wallet;

import net.pool.station.core.domain.DomainKey;

import java.util.Optional;

public interface WalletUseCase {
    void save(DomainKey<Long> accountId, Wallet wallet);

    Optional<Wallet> findByAccountId(DomainKey<Long> accountId);

    void updateBalance(DomainKey<Long> accountId, Double balance);

    void enable(DomainKey<Long> accountId);

    void disable(DomainKey<Long> accountId);

    void enableDirectPayment();
}
