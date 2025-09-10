package net.pool.station.core.features.login.log.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.login.log.LoginLog;
import net.pool.station.core.domain.login.log.LoginLogCriteria;
import net.pool.station.core.features.login.log.repository.database.LoginLogEntityMapper;
import net.pool.station.core.features.login.log.repository.database.LoginLogRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LoginLogQueryService {
    LoginLogRepository repository;

    LoginLogEntityMapper mapper;

    protected Page<LoginLog> findAll(LoginLogCriteria criteria, Pageable pageable) {
        return repository.findAll(criteria, pageable)
                .map(mapper::toDto);
    }
}
