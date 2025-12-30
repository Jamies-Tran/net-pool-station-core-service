package net.pool.station.core.features.match.making.match.making.controller.models;

import jakarta.validation.constraints.Min;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record MatchMakingRequest(
        Long stationId,
        Long gameId,
        @Min(value = 1, message = "Số ngày giữ chỗ phải ít nhất là 1 ngày.")
        Integer numberOfHoldingDay,
        LocalDate startAt,
        String resourceTypeCode,
        String resourceTypeName,
        String typeCode,
        String typeName,
        String paymentMethodCode,
        String paymentMethodName,
        String statusCode,
        String statusName,
        List<MatchMakingSlotRequest> slots,
        List<MatchMakingResourceRequest> resources
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
