package net.pool.station.core.features.wallet.ledger.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.utils.MyRequestContext;
import net.pool.station.core.bootstrap.utils.MySpringContext;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.transaction.Transaction;
import net.pool.station.core.domain.transaction.TransactionUseCase;
import net.pool.station.core.domain.wallet.WalletUseCase;
import net.pool.station.core.domain.wallet.ledger.WalletLedger;
import net.pool.station.core.domain.wallet.ledger.WalletLedgerCriteria;
import net.pool.station.core.domain.wallet.ledger.WalletLedgerUseCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WalletLedgerUseCaseService implements WalletLedgerUseCase {
    WalletLedgerCommandService commandService;

    WalletLedgerQueryService queryService;

    WalletUseCase walletUseCase;

    private TransactionUseCase transactionUseCase() {
        return MySpringContext.getBean(TransactionUseCase.class);
    }

    @Override
    @Transactional
    public void save(WalletLedger walletLedger) {
        WalletLedger saveLedger = commandService.save(walletLedger);
        walletUseCase.updateBalance(DomainKey.of(saveLedger.walletId()), saveLedger);
    }

    @Override
    @Transactional
    public void saveAll(List<WalletLedger> walletLedgers) {
        List<WalletLedger> savedWalletLedgers = commandService.saveAll(walletLedgers);
        walletUseCase.updateBalance(savedWalletLedgers);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<WalletLedger> findAll(WalletLedgerCriteria criteria, PageRequest pageRequest) {
        Page<WalletLedger> walletLedgers = queryService.findAll(criteria, pageRequest);
        List<Long> transactionIds = walletLedgers.map(WalletLedger::transactionId).toList();
        Map<Long, Transaction> transactionMap = transactionUseCase().findAllByIdIn(transactionIds)
                .stream()
                .collect(Collectors.toMap(Transaction::transactionId, Function.identity()));

        return walletLedgers.map(w -> w
                .withTransaction(transactionMap.computeIfAbsent(w.transactionId(), k -> null)));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<WalletLedger> findById(DomainKey<Long> walletLedgerId) {
        return Optional.empty();
    }
}
