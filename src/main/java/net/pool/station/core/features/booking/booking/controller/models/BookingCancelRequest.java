package net.pool.station.core.features.booking.booking.controller.models;

import jakarta.validation.constraints.NotNull;

public record BookingCancelRequest(
        @NotNull(message = "Lý do từ chối không được bỏ trống")
        String cancelReason
) {
}
