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
        @With String createdByUsername,
        String createdBy,
        LocalDateTime createdAt
) {
    public static LoginLog createLogin(Long accountId) {
        return LoginLog.builder()
                .accountId(accountId)
                .logTypeCode(ELogType.LOGIN_LOGIN.getCode())
                .logTypeName(ELogType.LOGIN_LOGIN.getName())
                .createdAt(LocalDateTime.now())
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
