package net.pool.station.core.domain.station.space.timeslot;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Builder;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;

import java.util.List;

@Builder
public record StationSpaceTimeSlotCriteria(
        Long scheduleId,
        Long stationId,
        Long spaceId,
        List<String> statusCodes
) {
    public StationSpaceTimeSlotCriteria {
        scheduleId = MyObjectUtils.defaultValue(scheduleId, new TypeReference<>() {});
        stationId = MyObjectUtils.defaultValue(stationId, new TypeReference<>() {});
        spaceId = MyObjectUtils.defaultValue(spaceId, new TypeReference<>() {});
        statusCodes = MyObjectUtils.defaultValue(statusCodes, new TypeReference<>() {});
    }

    public static StationSpaceTimeSlotCriteria of (
            Long scheduleId,
            Long stationId,
            Long spaceId,
            List<String> statusCodes
    ) {
        return StationSpaceTimeSlotCriteria.builder()
                .scheduleId(scheduleId)
                .stationId(stationId)
                .spaceId(spaceId)
                .statusCodes(statusCodes)
                .build();
    }
}
