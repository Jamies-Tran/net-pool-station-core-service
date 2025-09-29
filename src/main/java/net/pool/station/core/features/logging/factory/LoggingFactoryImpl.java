package net.pool.station.core.features.logging.factory;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.account.log.AccountLog;
import net.pool.station.core.domain.account.log.AccountLogCriteria;
import net.pool.station.core.domain.account.log.AccountLogUseCase;
import net.pool.station.core.domain.logging.factory.LoggingFactory;
import net.pool.station.core.domain.login.log.LoginLog;
import net.pool.station.core.domain.login.log.LoginLogCriteria;
import net.pool.station.core.domain.login.log.LoginLogUseCase;
import net.pool.station.core.domain.station.log.StationLog;
import net.pool.station.core.domain.station.log.StationLogCriteria;
import net.pool.station.core.domain.station.log.StationLogUseCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@SuppressWarnings("unchecked")
public class LoggingFactoryImpl<T> implements LoggingFactory<T> {
    AccountLogUseCase accountLogUseCase;

    LoginLogUseCase loginLogUseCase;

    StationLogUseCase stationLogUseCase;

    @Override
    public void log(T data) {
        if (data instanceof AccountLog accountLog) {
            accountLogUseCase.save(accountLog);
        }

        if (data instanceof LoginLog loginLog) {
            loginLogUseCase.save(loginLog);
        }

        if (data instanceof StationLog stationLog) {
            stationLogUseCase.save(stationLog);
        }
    }

    @Override
    public <C> Page<T> findAll(C criteria, PageRequest pageRequest) {
        if (criteria instanceof AccountLogCriteria accountCriteria) {
            return (Page<T>) accountLogUseCase.findAll(accountCriteria, pageRequest);
        }

        if (criteria instanceof LoginLogCriteria loginLogCriteria) {
            return (Page<T>) loginLogUseCase.findAll(loginLogCriteria, pageRequest);
        }

        if (criteria instanceof StationLogCriteria stationLogCriteria) {
            return (Page<T>) stationLogUseCase.findAll(stationLogCriteria, pageRequest);
        }

        return Page.empty();
    }
}
