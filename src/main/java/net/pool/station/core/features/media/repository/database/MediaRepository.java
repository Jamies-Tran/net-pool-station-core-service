package net.pool.station.core.features.media.repository.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediaRepository extends JpaRepository<MediaEntity, Long> {
    List<MediaEntity> findAllByStationId(Long stationId);

    List<MediaEntity> findAllByStationIdIn(List<Long> stationIds);
}
