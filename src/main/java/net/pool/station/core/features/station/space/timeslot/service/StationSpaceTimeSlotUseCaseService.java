package net.pool.station.core.features.station.space.timeslot.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.enums.ETimeSlotStatus;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.station.space.timeslot.StationSpaceTimeSlot;
import net.pool.station.core.domain.station.space.timeslot.StationSpaceTimeSlotCriteria;
import net.pool.station.core.domain.station.space.timeslot.StationSpaceTimeSlotUseCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StationSpaceTimeSlotUseCaseService implements StationSpaceTimeSlotUseCase {
    StationSpaceTimeSlotCommandService commandService;

    StationSpaceTimeSlotQueryService queryService;

    @Override
    @Transactional
    public void save(List<StationSpaceTimeSlot> stationSpaceTimeSlots) {
        commandService.save(stationSpaceTimeSlots);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StationSpaceTimeSlot> findAll(StationSpaceTimeSlotCriteria criteria, PageRequest pageRequest) {
        return queryService.findAll(criteria, pageRequest);
    }

    @Override
    @Transactional
    public void enable(DomainKey<Long> id) {
        commandService.updateStatus(id.value(), ETimeSlotStatus.ENABLED);
    }

    @Override
    @Transactional
    public void disable(DomainKey<Long> id) {
        commandService.updateStatus(id.value(), ETimeSlotStatus.DISABLED);
    }

    @Override
    @Transactional
    public void delete(DomainKey<Long> id) {
        commandService.delete(id.value());
    }
}
