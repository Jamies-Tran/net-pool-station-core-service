package net.pool.station.core.features.media.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.media.Media;
import net.pool.station.core.domain.media.MediaUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MediaUseCaseService implements MediaUseCase {
    MediaCommandService commandService;

    MediaQueryService queryService;

    @Override
    @Transactional
    public void saveAll(DomainKey<Long> stationId, List<Media> medias) {
        commandService.saveAll(stationId.value(), medias);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Media> findAllByStationId(DomainKey<Long> stationId) {
        return queryService.findAllByStationId(stationId.value());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Media> findAllByStationIdIn(List<Long> stationIds) {
        return queryService.findAllByStationIdIn(stationIds);
    }
}
