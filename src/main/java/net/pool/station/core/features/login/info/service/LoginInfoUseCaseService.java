package net.pool.station.core.features.login.info.service;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.login.info.LoginInfo;
import net.pool.station.core.domain.login.info.LoginInfoUseCase;
import org.springframework.security.core.context.SecurityContext;
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
        commandService.update(getPrincipal(), latitude, longitude);
    }

    @Override
    @Transactional
    public LoginInfo update(@NonNull String refreshToken) {
        return commandService.update(refreshToken);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LoginInfo> findCurrentLoginInfo() {
        return queryService.findByEmail(getPrincipal());
    }

    @Override
    @Transactional
    public void delete() {
        commandService.delete(getPrincipal());
        SecurityContextHolder.clearContext();
    }

    private String getPrincipal() {
        SecurityContext context = SecurityContextHolder.getContext();
        return (String) context.getAuthentication().getPrincipal();
    }
}
