package net.pool.station.core.features.transaction.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.transaction.Transaction;
import net.pool.station.core.features.transaction.repository.database.TransactionEntity;
import net.pool.station.core.features.transaction.repository.database.TransactionEntityMapper;
import net.pool.station.core.features.transaction.repository.database.TransactionRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionCommandService {
    TransactionRepository repository;

    TransactionEntityMapper mapper;

    protected Transaction save(Transaction transaction) {
        TransactionEntity savedTransaction = repository.save(mapper.toEntity(transaction));

        return mapper.toDto(savedTransaction);
    }

    protected Transaction update(String transactionCode, Transaction transaction) {

        return repository.findByTransactionCode(transactionCode)
                .map(foundEntity -> {
                    mapper.update(foundEntity, transaction);
                    TransactionEntity saveEntity = repository.save(foundEntity);

                    return mapper.toDto(saveEntity);
                })
                .orElse(null);
    }
}
