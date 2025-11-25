package net.pool.station.core.features.station.space.schedule.slot.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.station.space.schedule.slot.StationSpaceScheduleSlot;
import net.pool.station.core.domain.station.space.schedule.slot.StationSpaceScheduleSlotUseCase;
import net.pool.station.core.features.station.space.schedule.slot.controller.models.StationSpaceScheduleSlotListRequest;
import net.pool.station.core.features.station.space.schedule.slot.controller.models.StationSpaceScheduleSlotRequestMapper;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StationSpaceScheduleSlotsController implements StationSpaceScheduleSlotsApi {
    StationSpaceScheduleSlotUseCase stationSpaceScheduleSlotUseCase;

    StationSpaceScheduleSlotRequestMapper requestMapper;

    @Override
    public MyValueResponse<?> save(Long stationSpaceId,
                                   Long scheduleId,
                                   StationSpaceScheduleSlotListRequest request
    ) {
        List<StationSpaceScheduleSlot> slots = requestMapper.toDto(request.stationSpaceScheduleSlots());
        stationSpaceScheduleSlotUseCase.save(
                DomainKey.of(stationSpaceId),
                DomainKey.of(scheduleId),
                slots
        );

        return MyValueResponse.successNoData();
    }

    @Override
    public MyValueResponse<?> delete(Long stationSpaceId, Long timeSlotId) {
        stationSpaceScheduleSlotUseCase
                .delete(DomainKey.of(stationSpaceId), DomainKey.of(timeSlotId));

        return MyValueResponse.successNoData();
    }

    @Override
    public MyValueResponse<?> deleteAll(Long stationSpaceId, Long scheduleId) {
        stationSpaceScheduleSlotUseCase
                .deleteAll(DomainKey.of(stationSpaceId), DomainKey.of(scheduleId));

        return MyValueResponse.successNoData();
    }
}
