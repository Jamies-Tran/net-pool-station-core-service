package net.pool.station.core.features.media.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.media.Media;
import net.pool.station.core.features.media.repository.database.MediaEntity;
import net.pool.station.core.features.media.repository.database.MediaMapper;
import net.pool.station.core.features.media.repository.database.MediaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MediaCommandService {
    MediaRepository repository;

    MediaMapper mapper;

    protected void saveAll(Long stationId, List<Media> medias) {
        List<MediaEntity> entityList = repository.findAllByStationId(stationId);
        repository.deleteAll(entityList);
        medias = medias.stream()
                .map(m -> m.withStationId(stationId))
                .toList();

        repository.saveAll(mapper.toEntity(medias));
    }
}
