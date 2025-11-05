package net.pool.station.core.domain.transaction;

import net.pool.station.core.domain.DomainKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public interface TransactionUseCase {
    Transaction save(Transaction transaction);

    Page<Transaction> findAll(TransactionCriteria criteria, PageRequest pageRequest);

    Optional<Transaction> findByTransactionCode(DomainKey<String> transactionCode);

    void update(DomainKey<String> transactionCode, Transaction transaction);
}
