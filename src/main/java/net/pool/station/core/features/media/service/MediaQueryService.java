package net.pool.station.core.features.media.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.media.Media;
import net.pool.station.core.features.media.repository.database.MediaMapper;
import net.pool.station.core.features.media.repository.database.MediaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MediaQueryService {
    MediaRepository repository;

    MediaMapper mapper;

    protected List<Media> findAllByStationId(Long stationId) {
        return mapper.toDto(repository.findAllByStationId(stationId));
    }

    protected List<Media> findAllByStationIdIn(List<Long> stationIds) {
        return mapper.toDto(repository.findAllByStationIdIn(stationIds));
    }
}
