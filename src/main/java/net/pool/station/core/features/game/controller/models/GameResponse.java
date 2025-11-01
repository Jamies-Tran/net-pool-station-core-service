package net.pool.station.core.features.game.controller.models;

public record GameResponse(
        Long gameId,

        Long stationSpaceId,

        String gameCode,

        String gameName,

        String genreCode,

        String genreName,

        String statusCode,

        String statusName
) {
}
