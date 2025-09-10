package net.pool.station.core.domain.account.log;

import lombok.Builder;
import lombok.With;
import net.pool.station.core.bootstrap.enums.EActionLog;

import java.time.LocalDateTime;

@Builder
public record AccountLog(
        Long accountLogId,
        Long accountId,
        String actionCode,
        String actionName,
        @With String createdByUsername,
        String createdBy,
        LocalDateTime createdAt
) {
    public static AccountLog createSave(Long accountId) {
        return AccountLog.builder()
                .accountId(accountId)
                .actionCode(EActionLog.ACCOUNT_SAVE.getCode())
                .actionName(EActionLog.ACCOUNT_SAVE.getName())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static AccountLog createUpdate(Long accountId) {
        return AccountLog.builder()
                .accountId(accountId)
                .actionCode(EActionLog.ACCOUNT_UPDATE.getCode())
                .actionName(EActionLog.ACCOUNT_UPDATE.getName())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static AccountLog createEnable(Long accountId) {
        return AccountLog.builder()
                .accountId(accountId)
                .actionCode(EActionLog.ACCOUNT_ENABLE.getCode())
                .actionName(EActionLog.ACCOUNT_ENABLE.getName())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static AccountLog createDisable(Long accountId) {
        return AccountLog.builder()
                .accountId(accountId)
                .actionCode(EActionLog.ACCOUNT_DISABLE.getCode())
                .actionName(EActionLog.ACCOUNT_DISABLE.getName())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static AccountLog createVerify(Long accountId) {
        return AccountLog.builder()
                .accountId(accountId)
                .actionCode(EActionLog.ACCOUNT_VERIFY.getCode())
                .actionName(EActionLog.ACCOUNT_VERIFY.getName())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
