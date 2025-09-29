package net.pool.station.core.features.station.station.controller.models;

import jakarta.validation.constraints.NotNull;

public record StationRejectRequest(
        @NotNull(message = "Vui lòng nhập lý do từ chối")
        String rejectReason
) {
}
