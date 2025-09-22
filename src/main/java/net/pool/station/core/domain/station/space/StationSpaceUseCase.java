package net.pool.station.core.domain.station.space;

import jakarta.validation.constraints.NotNull;
import net.pool.station.core.domain.DomainCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface StationSpaceUseCase {
    void save(@NotNull StationSpace stationSpace);

    Optional<StationSpace> findById(@NotNull DomainCode<Long> id);

    Page<StationSpace> findAll(@NotNull StationSpaceCriteria criteria,@NotNull Pageable pageable);

    void update(@NotNull DomainCode<Long> id,@NotNull StationSpace stationSpace);

    void enable(@NotNull DomainCode<Long> id);

    void disable(@NotNull DomainCode<Long> id);

    void delete(@NotNull DomainCode<Long> id);

}
