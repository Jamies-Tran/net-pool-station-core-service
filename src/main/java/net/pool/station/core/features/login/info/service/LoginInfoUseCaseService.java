package net.pool.station.core.features.login.info.service;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import net.pool.station.core.bootstrap.utils.MyRequestContext;
import net.pool.station.core.domain.fcm.info.FcmInfo;
import net.pool.station.core.domain.fcm.info.FcmInfoUseCase;
import net.pool.station.core.domain.logging.factory.LoggingFactory;
import net.pool.station.core.domain.login.info.LoginInfo;
import net.pool.station.core.domain.login.info.LoginInfoUseCase;
import net.pool.station.core.domain.login.log.LoginLog;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LoginInfoUseCaseService implements LoginInfoUseCase {
    LoginInfoCommandService commandService;

    LoginInfoQueryService queryService;

    FcmInfoUseCase fcmInfoUseCase;

    LoggingFactory<LoginLog> loggingService;

    @Override
    @Transactional
    public LoginInfo saveOrUpdate(
            @NonNull String email,
            @NonNull String password,
            FcmInfo fcmInfo
    ) {
        LoginInfo savedLoginInfo = commandService.saveOrUpdate(email, password);
        if (MyObjectUtils.isNotEmpty(fcmInfo)) {
            fcmInfoUseCase.save(fcmInfo.withAccountId(savedLoginInfo.accountId()));
        }
        loggingService.log(LoginLog.createLogin(savedLoginInfo.accountId()));

        return savedLoginInfo;
    }

    @Override
    @Transactional
    public LoginInfo update(@NonNull String refreshToken) {
        return commandService.update(refreshToken);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LoginInfo> findCurrentLoginInfo() {
        return queryService.findByAccountId(getCurrentAccountId());
    }

    @Override
    @Transactional
    public void delete() {
        commandService.delete(getCurrentAccountId());
        loggingService.log(LoginLog.createLogout(getCurrentAccountId()));
        SecurityContextHolder.clearContext();
    }

    private Long getCurrentAccountId() {
        return MyRequestContext.getCurrentAccountId()
                .orElse(0L);
    }
}
