package net.pool.station.core.domain.station.space.timeslot;

import net.pool.station.core.domain.DomainKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface StationSpaceTimeSlotUseCase {
    void save(List<StationSpaceTimeSlot> stationSpaceTimeSlots);

    Page<StationSpaceTimeSlot> findAll(StationSpaceTimeSlotCriteria criteria, PageRequest pageRequest);

    void enable(DomainKey<Long> id);

    void disable(DomainKey<Long> id);

    void delete(DomainKey<Long> id);
}
