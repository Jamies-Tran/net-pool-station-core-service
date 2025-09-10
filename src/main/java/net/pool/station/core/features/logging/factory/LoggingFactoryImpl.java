package net.pool.station.core.features.logging.factory;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.account.log.AccountLog;
import net.pool.station.core.domain.account.log.AccountLogUseCase;
import net.pool.station.core.domain.logging.factory.LoggingFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LoggingFactoryImpl<T> implements LoggingFactory<T> {
    AccountLogUseCase accountLogUseCase;

    @Override
    public void log(T data) {
        if (data instanceof AccountLog accountLog) {
            accountLogUseCase.save(accountLog);
        }
    }
}
