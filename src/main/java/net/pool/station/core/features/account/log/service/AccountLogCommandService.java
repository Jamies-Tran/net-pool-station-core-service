package net.pool.station.core.features.account.log.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.account.log.AccountLog;
import net.pool.station.core.features.account.log.repository.database.AccountLogEntityMapper;
import net.pool.station.core.features.account.log.repository.database.AccountLogRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountLogCommandService {
    AccountLogRepository repository;

    AccountLogEntityMapper mapper;

    protected void save(AccountLog accountLog) {
        repository.save(mapper.toEntity(accountLog));
    }
}
