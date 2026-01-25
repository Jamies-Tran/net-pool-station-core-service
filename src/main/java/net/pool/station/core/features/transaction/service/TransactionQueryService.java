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

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionQueryService {
    TransactionRepository repository;

    TransactionEntityMapper mapper;

    protected List<Transaction> findAllByIdIn(List<Long> transactionIds) {
        return mapper.toDto(repository.findAllByTransactionIdIn(transactionIds));
    }

    protected Optional<Transaction> findByMatchMakingIdAndPaymentType(Long matchMakingId, EPaymentType paymentType) {
        return repository.findByMatchMakingIdAndPaymentTypeCode(matchMakingId, paymentType.getCode())
                .map(mapper::toDto);
    }

    protected Optional<Transaction> findByMatchParticipantIdAndPaymentType(Long matchParticipantId,
                                                                           EPaymentType paymentType) {
        return repository.findByMatchParticipantIdAndPaymentTypeCode(matchParticipantId, paymentType.getCode())
                .map(mapper::toDto);
    }

    protected List<Transaction> findAllByMatchMakingId(Long matchMakingId) {
        return mapper.toDto(repository.findAllByMatchMakingId(matchMakingId));
    }

    protected List<Transaction> findAllByMatchMakingIdAndMatchParticipantId(List<Long> matchMakingId, List<Long> matchParticipantId) {
        return mapper.toDto(repository.findAllByMatchMakingIdInAndMatchParticipantIdIn(matchMakingId, matchParticipantId));
    }

    protected Page<Transaction> findAll(TransactionCriteria criteria, PageRequest pageRequest) {
        return repository.findAll(criteria, pageRequest)
                .map(mapper::toDto);
    }

    protected List<Transaction> findAllBy(Long matchMakingId, List<EPaymentType> paymentTypes) {
        List<String> paymentTypeCodes = paymentTypes.stream().map(EPaymentType::getCode).toList();
        return mapper.toDto(repository.findAllByMatchMakingIdAndPaymentTypeCodeIn(matchMakingId, paymentTypeCodes));
    }
}
