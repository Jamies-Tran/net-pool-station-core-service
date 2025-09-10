package net.pool.station.core.features.login.log.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.login.log.LoginLog;
import net.pool.station.core.features.login.log.repository.database.LoginLogEntityMapper;
import net.pool.station.core.features.login.log.repository.database.LoginLogRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LoginLogCommandService {
    LoginLogRepository repository;

    LoginLogEntityMapper mapper;

    protected void save(LoginLog loginLog) {
        repository.save(mapper.toEntity(loginLog));
    }
}
