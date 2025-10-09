package net.pool.station.core.features.station.menu.repository.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StationMenuRepository extends JpaRepository<StationMenuEntity, Long>,
        JpaSpecificationExecutor<StationMenuEntity> {
    Boolean existsByMenuCodeAndDeletedFalse(String menuCode);

    Optional<StationMenuEntity> findByStationMenuIdAndDeletedFalse(Long stationMenuId);
}
