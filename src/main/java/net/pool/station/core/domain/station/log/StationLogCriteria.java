package net.pool.station.core.domain.station.log;

import com.fasterxml.jackson.core.type.TypeReference;
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
        search = MyObjectUtils.defaultValue(search, new TypeReference<>() {});
        timeRange = MyDateTimeUtils.defaultTimeRange(timeRange);
        logTypeCodes = MyObjectUtils.defaultValue(logTypeCodes, new TypeReference<>() {});
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
