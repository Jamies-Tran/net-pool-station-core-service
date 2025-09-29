package net.pool.station.core.features.station.space.service;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.enums.EStationSpaceStatus;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.station.space.StationSpace;
import net.pool.station.core.domain.station.space.StationSpaceCriteria;
import net.pool.station.core.domain.station.space.StationSpaceId;
import net.pool.station.core.domain.station.space.StationSpaceUseCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StationSpaceUseCaseService implements StationSpaceUseCase {
    StationSpaceCommandService commandService;

    StationSpaceQueryService queryService;

    @Override
    @Transactional
    public void save(@NotNull StationSpace stationSpace) {
        commandService.save(stationSpace);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StationSpace> findById(@NotNull DomainKey<StationSpaceId> id) {
        return queryService.findById(id.value());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StationSpace> findAll(@NotNull StationSpaceCriteria criteria,@NotNull Pageable pageable) {
        return queryService.findAll(criteria, pageable);
    }

    @Override
    @Transactional
    public void update(@NotNull DomainKey<StationSpaceId> id, @NotNull StationSpace stationSpace) {
        commandService.update(id.value(), stationSpace);
    }

    @Override
    @Transactional
    public void enable(@NotNull DomainKey<StationSpaceId> id) {
        commandService.updateStatus(id.value(), EStationSpaceStatus.ACTIVE);
    }

    @Override
    @Transactional
    public void disable(@NotNull DomainKey<StationSpaceId> id) {
        commandService.updateStatus(id.value(), EStationSpaceStatus.INACTIVE);
    }

    @Override
    @Transactional
    public void delete(@NotNull DomainKey<StationSpaceId> id) {
        commandService.delete(id.value());
    }
}
