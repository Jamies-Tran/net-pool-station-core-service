package net.pool.station.core.features.match.making.match.making.controller.models;

import jakarta.validation.constraints.Min;
import net.pool.station.core.features.match.making.match.making.controller.models.schedule.MatchScheduleRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record MatchMakingRequest(
        Long stationId,
        Long gameId,
        String resourceTypeCode,
        String resourceTypeName,
        String typeCode,
        String typeName,
        String paymentMethodCode,
        String paymentMethodName,
        String statusCode,
        String statusName,
        List<MatchMakingSlotRequest> slots,
        List<MatchMakingResourceRequest> resources,
        List<MatchScheduleRequest> schedules
) {
    public record MatchMakingSlotRequest(
            MatchMakingSlotRequestId id
    ) {
        public record MatchMakingSlotRequestId(
                Long timeSlotId
        ) {}
    }

    public record MatchMakingResourceRequest(
            MatchMakingResourceRequestId id
    ) {
        public record MatchMakingResourceRequestId(
                Long stationResourceId
        ) {}
    }
}
