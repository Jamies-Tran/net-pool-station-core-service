package net.pool.station.core.domain.station.log;

import lombok.Builder;
import lombok.With;
import net.pool.station.core.bootstrap.enums.ELogType;

import java.time.LocalDateTime;

@Builder
public record StationLog(
        Long stationLogId,
        Long stationId,
        String logTypeCode,
        String logTypeName,
        String rejectReason,
        String createdBy,
        @With String createdByUsername,
        LocalDateTime createdAt
) {
    public static StationLog createSave(Long stationId) {
        return StationLog.builder()
                .stationId(stationId)
                .logTypeCode(ELogType.STATION_SAVE.getCode())
                .logTypeName(ELogType.STATION_SAVE.getName())
                .build();
    }

    public static StationLog createUpdate(Long stationId) {
        return StationLog.builder()
                .stationId(stationId)
                .logTypeCode(ELogType.STATION_UPDATE.getCode())
                .logTypeName(ELogType.STATION_UPDATE.getName())
                .build();
    }

    public static StationLog createAccept(Long stationId) {
        return StationLog.builder()
                .stationId(stationId)
                .logTypeCode(ELogType.STATION_ACCEPT.getCode())
                .logTypeName(ELogType.STATION_ACCEPT.getName())
                .build();
    }

    public static StationLog createReject(Long stationId, String rejectReason) {
        return StationLog.builder()
                .stationId(stationId)
                .logTypeCode(ELogType.STATION_REJECT.getCode())
                .logTypeName(ELogType.STATION_REJECT.getName())
                .rejectReason(rejectReason)
                .build();
    }

    public static StationLog createActive(Long stationId) {
        return StationLog.builder()
                .stationId(stationId)
                .logTypeCode(ELogType.STATION_ACTIVE.getCode())
                .logTypeName(ELogType.STATION_ACTIVE.getName())
                .build();
    }

    public static StationLog createInActive(Long stationId) {
        return StationLog.builder()
                .stationId(stationId)
                .logTypeCode(ELogType.STATION_INACTIVE.getCode())
                .logTypeName(ELogType.STATION_INACTIVE.getName())
                .build();
    }
}
