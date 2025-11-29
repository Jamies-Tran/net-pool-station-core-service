package net.pool.station.core.features.area.controller.models;

import jakarta.validation.constraints.NotNull;

public record AreaRequest(
        @NotNull(message = "Thông tin không gian hoạt động không được bỏ trống")
        Long stationSpaceId,
        Integer price,
        @NotNull(message = "Mã không được bỏ trống")
        String areaCode,
        @NotNull(message = "Tên không được bỏ trống")
        String areaName
) {
}
