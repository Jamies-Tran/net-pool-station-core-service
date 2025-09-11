package net.pool.station.core.features.login.log.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.utils.MySpringContext;
import net.pool.station.core.domain.login.log.LoginLog;
import net.pool.station.core.domain.map.reverse.ReverseGeo;
import net.pool.station.core.domain.map.reverse.ReverseGeoUseCase;
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
        ReverseGeoUseCase reverseGeoUseCase = MySpringContext.getBean(ReverseGeoUseCase.class);
        String address = reverseGeoUseCase.reverseGeo(loginLog.latitude(), loginLog.longitude())
                .map(ReverseGeo.Result::address)
                .orElse("");
        repository.save(mapper.toEntity(loginLog.withAddress(address)));
    }
}
