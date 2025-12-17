package net.pool.station.core.domain.login.info;

import lombok.NonNull;
import net.pool.station.core.domain.fcm.info.FcmInfo;

import java.util.Optional;

public interface LoginInfoUseCase {
    LoginInfo saveOrUpdate(
            @NonNull String email,
            @NonNull String password,
            FcmInfo fcmInfo
    );

    LoginInfo update(@NonNull String refreshToken);

    Optional<LoginInfo> findCurrentLoginInfo();

    void delete();
}
