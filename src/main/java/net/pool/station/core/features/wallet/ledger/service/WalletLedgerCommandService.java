package net.pool.station.core.features.wallet.ledger.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.wallet.ledger.WalletLedger;
import net.pool.station.core.features.wallet.ledger.repository.database.WalletLedgerEntity;
import net.pool.station.core.features.wallet.ledger.repository.database.WalletLedgerEntityMapper;
import net.pool.station.core.features.wallet.ledger.repository.database.WalletLedgerRepository;
import net.pool.station.core.features.wallet.ledger.repository.database.dao.CurrentBalanceAndCommissionDao;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WalletLedgerCommandService {
    WalletLedgerRepository repository;

    WalletLedgerEntityMapper mapper;

    protected WalletLedger save(WalletLedger walletLedger) {
        CurrentBalanceAndCommissionDao currentBalanceAndCommission = repository
                .findCurrentBalanceByWalletId(walletLedger.walletId());
        Integer changeAmount = walletLedger.changeAmount();
        Integer chargeCommission = walletLedger.chargedCommission();
        Integer currentBalance = currentBalanceAndCommission.getCurrentBalance();
        Integer currentContributedCommission = currentBalanceAndCommission.getCurrentContributedCommission();
        Integer newBalance = currentBalance + changeAmount - chargeCommission;
        Integer newContributedCommission = currentContributedCommission + chargeCommission;
        WalletLedgerEntity saveLedger = repository.save(mapper
                .toEntity(walletLedger
                        .withCurrentBalance(currentBalance)
                        .withNewBalance(newBalance)
                        .withChargedCommission(chargeCommission)
                        .withNewContributedCommission(newContributedCommission)));

        return mapper.toDto(saveLedger);
    }
}
