package net.pool.station.core.domain.login.log;

import lombok.Builder;
import lombok.With;
import net.pool.station.core.bootstrap.enums.ELogType;

import java.time.LocalDateTime;
import java.util.Optional;

@Builder
public record LoginLog(
        Long loginLogId,
        Long accountId,
        String logTypeCode,
        String logTypeName,
        Double latitude,
        Double longitude,
        @With String address,
        @With String createdByUsername,
        String createdBy,
        LocalDateTime createdAt
) {
    public static LoginLog createLogin(Long accountId, Double latitude, Double longitude) {
        return LoginLog.builder()
                .accountId(accountId)
                .logTypeCode(ELogType.LOGIN_LOGIN.getCode())
                .logTypeName(ELogType.LOGIN_LOGIN.getName())
                .createdAt(LocalDateTime.now())
                .latitude(Optional.ofNullable(latitude).orElse(0.0))
                .longitude(Optional.ofNullable(longitude).orElse(0.0))
                .build();
    }

    public static LoginLog createLogout(Long accountId) {
        return LoginLog.builder()
                .accountId(accountId)
                .logTypeCode(ELogType.LOGIN_LOGOUT.getCode())
                .logTypeName(ELogType.LOGIN_LOGOUT.getName())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
