package net.pool.station.core.features.account.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.account.Account;
import net.pool.station.core.domain.account.AccountCriteria;
import net.pool.station.core.features.account.repository.database.AccountEntity;
import net.pool.station.core.features.account.repository.database.AccountEntityMapper;
import net.pool.station.core.features.account.repository.database.AccountRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountQueryService {
    AccountRepository repository;

    AccountEntityMapper mapper;

    protected Optional<Account> findByEmail(String email) {
        return repository.findByEmail(email)
                .map(mapper::toDto);
    }

    protected Optional<Account> findById(Long accountId) {
        return repository.findByAccountId(accountId)
                .map(mapper::toDto);
    }

    protected Page<Account> findAll(AccountCriteria criteria, Pageable pageable) {
        return repository.findAll(criteria, pageable)
                .map(mapper::toDto);
    }
}
