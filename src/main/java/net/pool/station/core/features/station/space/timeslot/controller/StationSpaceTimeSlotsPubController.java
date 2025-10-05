package net.pool.station.core.features.station.space.timeslot.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.rest.response.MyPageResponse;
import net.pool.station.core.bootstrap.rest.response.MySorter;
import net.pool.station.core.domain.station.space.timeslot.StationSpaceTimeSlotCriteria;
import net.pool.station.core.domain.station.space.timeslot.StationSpaceTimeSlotUseCase;
import net.pool.station.core.features.station.space.timeslot.controller.models.StationSpaceTimeSlotResponse;
import net.pool.station.core.features.station.space.timeslot.controller.models.StationSpaceTimeSlotResponseMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StationSpaceTimeSlotsPubController implements StationSpaceTimeSlotsPubApi {
    StationSpaceTimeSlotUseCase stationSpaceTimeSlotUseCase;

    StationSpaceTimeSlotResponseMapper responseMapper;

    @Override
    public MyPageResponse<StationSpaceTimeSlotResponse> findAll(
            Long scheduleId,
            Long stationId,
            Long spaceId,
            List<String> statusCodes,
            String sorter, Integer current, Integer pageSize
    ) {
        StationSpaceTimeSlotCriteria criteria = StationSpaceTimeSlotCriteria.of(scheduleId, stationId, spaceId,
                statusCodes);
        PageRequest pageRequest = PageRequest.of(current, pageSize, MySorter.of(sorter));
        Page<StationSpaceTimeSlotResponse> responses = stationSpaceTimeSlotUseCase.findAll(criteria, pageRequest)
                .map(responseMapper::toModel);

        return MyPageResponse.success(responses);
    }
}
