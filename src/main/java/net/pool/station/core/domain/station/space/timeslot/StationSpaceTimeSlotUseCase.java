package net.pool.station.core.domain.station.space.timeslot;

import net.pool.station.core.domain.DomainKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface StationSpaceTimeSlotUseCase {
    void save(List<StationSpaceTimeSlot> stationSpaceTimeSlots);

    Page<StationSpaceTimeSlot> findAll(StationSpaceTimeSlotCriteria criteria, PageRequest pageRequest);

    void enable(DomainKey<StationSpaceTimeSlotId> id);

    void disable(DomainKey<StationSpaceTimeSlotId> id);

    void delete(DomainKey<StationSpaceTimeSlotId> id);
}
