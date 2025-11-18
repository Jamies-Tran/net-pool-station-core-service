package net.pool.station.core.domain.schedule;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Builder;
import net.pool.station.core.bootstrap.utils.MyDateTimeUtils;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.List;

@Builder
public record ScheduleCriteria(
        Long stationId,
        Long stationResourceId,
        Long stationSpaceId,
        List<LocalDate> dateRange,
        List<String> statusCodes
) {
    public ScheduleCriteria {
        dateRange = MyDateTimeUtils.defaultDateRange(dateRange);
        statusCodes = MyObjectUtils.defaultValue(statusCodes, new TypeReference<>() {});
    }

    public static ScheduleCriteria ofStation(
            Long stationId,
            List<LocalDate> dateRange,
            List<String> statusCodes
    ) {
        return ScheduleCriteria
                .builder()
                .stationId(stationId)
                .dateRange(dateRange)
                .statusCodes(statusCodes)
                .build();
    }

    public static ScheduleCriteria ofStationResource (
            Long stationResourceId,
            List<LocalDate> dateRange,
            List<String> statusCodes
    ) {
        return ScheduleCriteria
                .builder()
                .stationResourceId(stationResourceId)
                .dateRange(dateRange)
                .statusCodes(statusCodes)
                .build();
    }

    public static ScheduleCriteria ofStationSpace (
            Long stationSpaceId,
            List<LocalDate> dateRange,
            List<String> statusCodes
    ) {
        return ScheduleCriteria
                .builder()
                .stationSpaceId(stationSpaceId)
                .dateRange(dateRange)
                .statusCodes(statusCodes)
                .build();
    }

    public LocalDate startFrom() {
        if (CollectionUtils.isEmpty(dateRange)) {
            return null;
        }
        return dateRange.get(0);
    }

    public LocalDate endTo() {
        if (CollectionUtils.isEmpty(dateRange) || dateRange.size() < 2) {
            return null;
        }
        return dateRange.get(1);
    }
}
