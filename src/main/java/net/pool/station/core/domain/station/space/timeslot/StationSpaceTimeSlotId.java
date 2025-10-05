package net.pool.station.core.domain.station.space.timeslot;

import lombok.Builder;

@Builder
public record StationSpaceTimeSlotId(
        Long timeSlotId,
        Long stationId,
        Long spaceId
) {
    public static StationSpaceTimeSlotId of(Long timeSlotId, Long stationId, Long spaceId) {
        return StationSpaceTimeSlotId.builder()
                .timeSlotId(timeSlotId)
                .stationId(stationId)
                .spaceId(spaceId)
                .build();
    }
}
