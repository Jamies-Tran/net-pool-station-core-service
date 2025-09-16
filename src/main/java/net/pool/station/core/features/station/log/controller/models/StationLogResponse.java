package net.pool.station.core.features.station.log.controller.models;

import java.time.LocalDateTime;

public record StationLogResponse(
        Long stationLogId,
        Long stationId,
        String logTypeCode,
        String logTypeName,
        String rejectReason,
        String createdBy,
        String createdByUsername,
        LocalDateTime createdAt
) {
}
