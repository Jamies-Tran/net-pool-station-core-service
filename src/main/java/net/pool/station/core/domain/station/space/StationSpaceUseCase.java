package net.pool.station.core.domain.station.space;

import jakarta.validation.constraints.NotNull;
import net.pool.station.core.domain.DomainKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface StationSpaceUseCase {
    void save(@NotNull StationSpace stationSpace);

    Optional<StationSpace> findById(@NotNull DomainKey<StationSpaceId> id);

    Page<StationSpace> findAll(@NotNull StationSpaceCriteria criteria,@NotNull Pageable pageable);

    void update(@NotNull DomainKey<StationSpaceId> id, @NotNull StationSpace stationSpace);

    void enable(@NotNull DomainKey<StationSpaceId> id);

    void disable(@NotNull DomainKey<StationSpaceId> id);

    void delete(@NotNull DomainKey<StationSpaceId> id);

}
