package net.pool.station.core.domain.login.info;

import lombok.NonNull;

import java.util.Optional;

public interface LoginInfoUseCase {
    LoginInfo saveOrUpdate(
            @NonNull String email,
            @NonNull String password,
            Double latitude,
            Double longitude
    );

    void update(@NonNull Double latitude, @NonNull Double longitude);

    LoginInfo update(@NonNull String refreshToken);

    Optional<LoginInfo> findCurrentLoginInfo();

    void delete();
}
