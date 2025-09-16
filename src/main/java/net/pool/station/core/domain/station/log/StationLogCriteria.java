package net.pool.station.core.domain.station.log;

import lombok.Builder;
import net.pool.station.core.bootstrap.utils.MyDateTimeUtils;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Builder
public record StationLogCriteria(
        String search,
        Long stationId,
        List<LocalDateTime> timeRange,
        List<String> logTypeCodes
) {
    public StationLogCriteria {
        search = MyObjectUtils.defaultValue(search);
        timeRange = MyDateTimeUtils.defaultTimeRange(timeRange);
        logTypeCodes = MyObjectUtils.defaultValue(logTypeCodes);
    }

    public static StationLogCriteria of(
            String search,
            Long stationId,
            List<LocalDateTime> timeRange,
            List<String> logTypeCodes
    ) {
        return StationLogCriteria.builder()
                .search(search)
                .stationId(stationId)
                .timeRange(timeRange)
                .logTypeCodes(logTypeCodes)
                .build();
    }
}
