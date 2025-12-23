package net.pool.station.core.features.station.station.controller.models;

import net.pool.station.core.domain.media.Media;

import java.util.List;

public record StationRequest(
        String avatar,
        String stationName,
        String address,
        String province,
        String commune,
        String district,
        String placeId,
        String hotline,
        List<Media> media
) {
}
