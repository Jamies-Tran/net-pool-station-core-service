package net.pool.station.core.features.transaction.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotFoundException;
import net.pool.station.core.bootstrap.enums.EPaymentType;
import net.pool.station.core.domain.transaction.Transaction;
import net.pool.station.core.domain.transaction.TransactionCriteria;
import net.pool.station.core.features.transaction.repository.database.TransactionEntityMapper;
import net.pool.station.core.features.transaction.repository.database.TransactionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionQueryService {
    TransactionRepository repository;

    TransactionEntityMapper mapper;

    protected Optional<Transaction> findByTransactionCode(String transactionCode) {
        return repository.findByTransactionCode(transactionCode)
                .map(mapper::toDto);
    }

    protected Optional<Transaction> findByMatchMakingIdAndPaymentType(Long matchMakingId, EPaymentType paymentType) {
        return repository.findByMatchMakingIdAndPaymentTypeCode(matchMakingId, paymentType.getCode())
                .map(mapper::toDto);
    }

    protected Page<Transaction> findAll(TransactionCriteria criteria, PageRequest pageRequest) {
        return repository.findAll(criteria, pageRequest)
                .map(mapper::toDto);
    }
}
