package net.pool.station.core.domain.account.log;

import lombok.Builder;
import lombok.With;
import net.pool.station.core.bootstrap.enums.ELogType;

import java.time.LocalDateTime;

@Builder
public record AccountLog(
        Long accountLogId,
        Long accountId,
        String logTypeCode,
        String logTypeName,
        @With String createdByUsername,
        String createdBy,
        LocalDateTime createdAt
) {
    public static AccountLog createSave(Long accountId) {
        return AccountLog.builder()
                .accountId(accountId)
                .logTypeCode(ELogType.ACCOUNT_SAVE.getCode())
                .logTypeName(ELogType.ACCOUNT_SAVE.getName())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static AccountLog createUpdate(Long accountId) {
        return AccountLog.builder()
                .accountId(accountId)
                .logTypeCode(ELogType.ACCOUNT_UPDATE.getCode())
                .logTypeName(ELogType.ACCOUNT_UPDATE.getName())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static AccountLog createEnable(Long accountId) {
        return AccountLog.builder()
                .accountId(accountId)
                .logTypeCode(ELogType.ACCOUNT_ENABLE.getCode())
                .logTypeName(ELogType.ACCOUNT_ENABLE.getName())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static AccountLog createDisable(Long accountId) {
        return AccountLog.builder()
                .accountId(accountId)
                .logTypeCode(ELogType.ACCOUNT_DISABLE.getCode())
                .logTypeName(ELogType.ACCOUNT_DISABLE.getName())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static AccountLog createVerify(Long accountId) {
        return AccountLog.builder()
                .accountId(accountId)
                .logTypeCode(ELogType.ACCOUNT_VERIFY.getCode())
                .logTypeName(ELogType.ACCOUNT_VERIFY.getName())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
