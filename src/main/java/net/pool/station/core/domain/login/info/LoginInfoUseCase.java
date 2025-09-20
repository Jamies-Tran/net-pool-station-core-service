package net.pool.station.core.domain.login.info;

import lombok.NonNull;

import java.util.Optional;

public interface LoginInfoUseCase {
    LoginInfo saveOrUpdate(
            @NonNull String email,
            @NonNull String password
    );

    LoginInfo update(@NonNull String refreshToken);

    Optional<LoginInfo> findCurrentLoginInfo();

    void delete();
}
