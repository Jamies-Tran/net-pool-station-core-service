package net.pool.station.core.features.account.log.controller.models;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record AccountLogResponse(
        Long accountLogId,
        Long accountId,
        String actionCode,
        String actionName,
        String createdByUsername,
        LocalDateTime createdAt
) {
}
