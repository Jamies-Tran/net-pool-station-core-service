package net.pool.station.core.features.login.info.service;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyAuthenticationException;
import net.pool.station.core.bootstrap.utils.MyRequestContext;
import net.pool.station.core.domain.login.info.LoginInfo;
import net.pool.station.core.domain.login.info.LoginInfoUseCase;
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

    @Override
    @Transactional
    public LoginInfo saveOrUpdate(
            @NonNull String email,
            @NonNull String password,
            Double latitude,
            Double longitude
    ) {
        return commandService.saveOrUpdate(email, password, latitude, longitude);
    }

    @Override
    @Transactional
    public void update(@NonNull Double latitude, @NonNull Double longitude) {
        Long currentAccountId = MyRequestContext.getCurrentAccountId()
                .orElseThrow(MyAuthenticationException::new);
        commandService.update(currentAccountId, latitude, longitude);
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
        SecurityContextHolder.clearContext();
    }

    private Long getCurrentAccountId() {
        return MyRequestContext.getCurrentAccountId()
                .orElseThrow(MyAuthenticationException::new);
    }
}
