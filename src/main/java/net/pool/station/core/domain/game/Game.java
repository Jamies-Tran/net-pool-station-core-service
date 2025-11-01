package net.pool.station.core.domain.game;

import lombok.Builder;

@Builder
public record Game(
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
