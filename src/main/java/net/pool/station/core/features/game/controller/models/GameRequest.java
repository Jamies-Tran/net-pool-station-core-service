package net.pool.station.core.features.game.controller.models;

import jakarta.validation.constraints.NotNull;

public record GameRequest(
        @NotNull(message = "Station space không được bỏ trống")
        Long stationSpaceId,

        @NotNull(message = "Tên game không được bỏ trống")
        String gameName,

        String genreCode,

        String genreName,

        String statusCode,

        String statusName
) {
}
