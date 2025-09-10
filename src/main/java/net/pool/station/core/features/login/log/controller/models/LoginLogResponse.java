package net.pool.station.core.features.login.log.controller.models;

import lombok.Builder;
import lombok.With;

import java.time.LocalDateTime;

@Builder
public record LoginLogResponse(
        Long loginLogId,
        Long accountId,
        String logTypeCode,
        String logTypeName,
        String createdByUsername,
        String createdBy,
        LocalDateTime createdAt
) {
}
