package net.pool.station.core.domain.account.log;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record AccountLog(
        Long accountLogId,
        Long accountId,
        String actionCode,
        String actionName,
        LocalDateTime createdAt
) {
}
