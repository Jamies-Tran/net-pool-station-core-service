package net.pool.station.core.features.wallet.ledger.service;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.wallet.ledger.WalletLedger;
import net.pool.station.core.domain.wallet.ledger.WalletLedgerCriteria;
import net.pool.station.core.features.wallet.ledger.repository.database.WalletLedgerEntityMapper;
import net.pool.station.core.features.wallet.ledger.repository.database.WalletLedgerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WalletLedgerQueryService {
    WalletLedgerRepository repository;

    WalletLedgerEntityMapper mapper;

    protected Page<WalletLedger> findAll(WalletLedgerCriteria criteria, PageRequest pageRequest) {
        return repository.findAll(criteria, pageRequest)
                .map(mapper::toDto);
    }

    protected List<WalletLedger> findAllByTransactionIdIn(List<Long> transactionIds) {
        return mapper.toDto(repository.findAllByTransactionIdIn(transactionIds));
    }
}
