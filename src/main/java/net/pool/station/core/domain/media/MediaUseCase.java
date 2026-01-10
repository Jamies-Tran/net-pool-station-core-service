package net.pool.station.core.domain.media;

import net.pool.station.core.domain.DomainKey;

import java.util.List;

public interface MediaUseCase {
    void saveAll(DomainKey<Long> stationId, List<Media> medias);

    List<Media> findAllByStationId(DomainKey<Long> stationId);

    List<Media> findAllByStationIdIn(List<Long> stationIds);
}
