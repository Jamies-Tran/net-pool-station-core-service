package net.pool.station.core.bootstrap.utils;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.login.info.LoginInfo;
import net.pool.station.core.domain.login.info.LoginInfoUseCase;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MyRequestContext {

    public static Optional<LoginInfo> currentLoginInfo() {
        LoginInfoUseCase loginInfoUseCase = MySpringContext.getBean(LoginInfoUseCase.class);

        return loginInfoUseCase.findCurrentLoginInfo();
    }

    public static Optional<Long> getCurrentAccountId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            return Optional.empty();
        }
        Long tryToConvertToLong = MyObjectUtils.convertToLong((String) auth.getPrincipal());

        if (MyObjectUtils.isEquals(0L , tryToConvertToLong)) {
            return Optional.empty();
        }
        return Optional.of(tryToConvertToLong);
    }
}
