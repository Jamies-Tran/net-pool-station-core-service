package net.pool.station.core.features.wallet.ledger.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.wallet.ledger.WalletLedger;
import net.pool.station.core.features.wallet.ledger.repository.database.WalletLedgerEntity;
import net.pool.station.core.features.wallet.ledger.repository.database.WalletLedgerEntityMapper;
import net.pool.station.core.features.wallet.ledger.repository.database.WalletLedgerRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WalletLedgerCommandService {
    WalletLedgerRepository repository;

    WalletLedgerEntityMapper mapper;

    protected WalletLedger save(WalletLedger walletLedger) {
        Integer currentBalance = repository.findCurrentBalanceByWalletId(walletLedger.walletId());
        WalletLedgerEntity saveLedger = repository.save(mapper
                .toEntity(walletLedger.withCurrentBalance(currentBalance)));

        return mapper.toDto(saveLedger);
    }
}
