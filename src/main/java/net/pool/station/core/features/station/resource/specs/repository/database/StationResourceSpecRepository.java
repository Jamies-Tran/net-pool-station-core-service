package net.pool.station.core.features.station.resource.specs.repository.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StationResourceSpecRepository extends JpaRepository<StationResourceSpecEntity, Long> {
    Optional<StationResourceSpecEntity> findByStationResourceIdAndDeletedFalse(Long stationResourceId);

    Optional<StationResourceSpecEntity> findByStationResourceSpecIdAndDeletedFalse(Long stationResourceSpecId);
}
