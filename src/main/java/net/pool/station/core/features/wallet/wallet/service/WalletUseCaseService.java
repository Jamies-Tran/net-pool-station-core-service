package net.pool.station.core.features.wallet.wallet.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.enums.EWalletStatus;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.wallet.Wallet;
import net.pool.station.core.domain.wallet.WalletUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WalletUseCaseService implements WalletUseCase {
    WalletCommandService commandService;

    WalletQueryService queryService;

    @Override
    @Transactional
    public void save(DomainKey<Long> accountId, Wallet wallet) {
        commandService.save(accountId.value(), wallet);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Wallet> findByAccountId(DomainKey<Long> accountId) {
        return queryService.findByAccountId(accountId.value());
    }

    @Override
    @Transactional
    public void updateBalance(DomainKey<Long> walletId, Integer balance) {
        commandService.updateBalance(walletId.value(), balance);
    }

    @Override
    @Transactional
    public void enable(DomainKey<Long> accountId) {
        commandService.updateStatus(accountId.value(), EWalletStatus.ENABLE);
    }

    @Override
    @Transactional
    public void disable(DomainKey<Long> accountId) {
        commandService.updateStatus(accountId.value(), EWalletStatus.DISABLE);
    }

    @Override
    public void enableDirectPayment() {

    }
}
