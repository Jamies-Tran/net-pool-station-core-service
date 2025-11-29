package net.pool.station.core.features.station.space.space.controller.models;

import jakarta.validation.constraints.NotNull;
import net.pool.station.core.domain.station.space.StationSpaceId;

public record StationSpaceRequest(
        @NotNull(message = "Thông tin station không được bỏ trống")
        Long stationId,

        @NotNull(message = "Thông tin space không được bỏ trống")
        Long spaceId,

        @NotNull(message = "Mã không được bỏ trống")
        String spaceCode,

        @NotNull(message = "Tên không được bỏ trống")
        String spaceName,
        Integer capacity,
        String statusCode,
        String statusName
) {
}
