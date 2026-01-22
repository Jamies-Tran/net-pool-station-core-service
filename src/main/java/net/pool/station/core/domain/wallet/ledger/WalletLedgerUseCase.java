package net.pool.station.core.domain.wallet.ledger;

import net.pool.station.core.domain.DomainKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

public interface WalletLedgerUseCase {
    WalletLedger save(WalletLedger walletLedger, Boolean isUpdateBalance);

    void saveAll(List<WalletLedger> walletLedgers);

    List<WalletLedger> updateBalanceByTransactionIds(List<Long> transactionIds);

    Page<WalletLedger> findAll(WalletLedgerCriteria criteria, PageRequest pageRequest);

    Optional<WalletLedger> findById(DomainKey<Long> walletLedgerId);
}
