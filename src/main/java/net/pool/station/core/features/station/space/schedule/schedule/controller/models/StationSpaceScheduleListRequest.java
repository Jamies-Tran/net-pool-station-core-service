package net.pool.station.core.features.station.space.schedule.schedule.controller.models;

import java.util.List;

public record StationSpaceScheduleListRequest(
        List<StationSpaceScheduleRequest> stationSpaceSchedules
) {
    public record StationSpaceScheduleRequest(Long stationSpaceId) {}
}
