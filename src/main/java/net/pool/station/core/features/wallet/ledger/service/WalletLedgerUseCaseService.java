package net.pool.station.core.features.wallet.ledger.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.wallet.WalletUseCase;
import net.pool.station.core.domain.wallet.ledger.WalletLedger;
import net.pool.station.core.domain.wallet.ledger.WalletLedgerUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WalletLedgerUseCaseService implements WalletLedgerUseCase {
    WalletLedgerCommandService commandService;

    WalletUseCase walletUseCase;

    @Override
    @Transactional
    public void save(WalletLedger walletLedger) {
        WalletLedger saveLedger = commandService.save(walletLedger);
        walletUseCase.updateBalance(DomainKey.of(saveLedger.walletId()), saveLedger.newBalance());
    }

    @Override
    @Transactional(readOnly = true)
    public List<WalletLedger> findAllByWalletId(Long walletId) {
        return List.of();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WalletLedger> findById(DomainKey<Long> walletLedgerId) {
        return Optional.empty();
    }
}
