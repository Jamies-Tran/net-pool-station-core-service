package net.pool.station.core.features.account.log.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.account.log.AccountLog;
import net.pool.station.core.domain.account.log.AccountLogCriteria;
import net.pool.station.core.features.account.log.repository.database.AccountLogEntityMapper;
import net.pool.station.core.features.account.log.repository.database.AccountLogRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountLogQueryService {
    AccountLogRepository repository;

    AccountLogEntityMapper mapper;

    protected Page<AccountLog> findAll(AccountLogCriteria criteria, Pageable pageable) {
        return repository.findAll(criteria, pageable)
                .map(mapper::toDto);
    }
}
