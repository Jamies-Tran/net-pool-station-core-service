package net.pool.station.core.features.station.space.timeslot.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.station.space.timeslot.StationSpaceTimeSlot;
import net.pool.station.core.domain.station.space.timeslot.StationSpaceTimeSlotCriteria;
import net.pool.station.core.features.station.space.timeslot.repository.database.StationSpaceTimeSlotEntityMapper;
import net.pool.station.core.features.station.space.timeslot.repository.database.StationSpaceTimeSlotRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StationSpaceTimeSlotQueryService {
    StationSpaceTimeSlotRepository repository;

    StationSpaceTimeSlotEntityMapper mapper;

    protected Page<StationSpaceTimeSlot> findAll(StationSpaceTimeSlotCriteria criteria, PageRequest pageRequest) {
        return repository.findAll(criteria, pageRequest)
                .map(mapper::toDto);
    }
}
