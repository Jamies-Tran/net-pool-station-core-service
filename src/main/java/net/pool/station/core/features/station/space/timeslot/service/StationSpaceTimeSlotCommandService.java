package net.pool.station.core.features.station.space.timeslot.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotFoundException;
import net.pool.station.core.bootstrap.enums.ETimeSlotStatus;
import net.pool.station.core.domain.station.space.timeslot.StationSpaceTimeSlot;
import net.pool.station.core.domain.station.space.timeslot.StationSpaceTimeSlotId;
import net.pool.station.core.features.station.space.timeslot.repository.database.StationSpaceTimeSlotEntityMapper;
import net.pool.station.core.features.station.space.timeslot.repository.database.StationSpaceTimeSlotRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StationSpaceTimeSlotCommandService {
    StationSpaceTimeSlotRepository repository;

    StationSpaceTimeSlotEntityMapper mapper;

    protected void save(List<StationSpaceTimeSlot> stationSpaceTimeSlots) {
        repository.saveAll(mapper.toEntity(stationSpaceTimeSlots));
    }

    protected void updateStatus(StationSpaceTimeSlotId id, ETimeSlotStatus status) {
        repository.findById(id)
                .ifPresentOrElse(
                        foundEntity -> {
                            foundEntity.setStatusCode(status.getCode());
                            foundEntity.setStatusName(status.getName());
                            repository.save(foundEntity);
                        },
                        () -> {
                            throw new MyResourceNotFoundException();
                        });
    }

    protected void delete(StationSpaceTimeSlotId id) {
        repository.findById(id).ifPresent(repository::delete);
    }
}
