package net.pool.station.core.features.login.info.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.login.info.LoginInfo;
import net.pool.station.core.features.login.info.repository.database.LoginInfoEntityMapper;
import net.pool.station.core.features.login.info.repository.database.LoginInfoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LoginInfoQueryService {
    LoginInfoRepository repository;

    LoginInfoEntityMapper mapper;

    protected Optional<LoginInfo> findByEmail(String email) {
        return repository.findByEmail(email)
                .map(mapper::toDto);
    }
}
