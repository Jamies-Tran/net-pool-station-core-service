package net.pool.station.core.features.schedule.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotFoundException;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.schedule.ScheduleUseCase;
import net.pool.station.core.features.schedule.controller.models.ScheduleResponse;
import net.pool.station.core.features.schedule.controller.models.ScheduleResponseMapper;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level =  AccessLevel.PRIVATE, makeFinal = true)
public class SchedulePubController implements SchedulePubApi {
    ScheduleUseCase scheduleUseCase;

    ScheduleResponseMapper responseMapper;

    @Override
    public MyValueResponse<ScheduleResponse> findById(Long scheduleId) {
        ScheduleResponse response = scheduleUseCase.findById(DomainKey.of(scheduleId))
                .map(responseMapper::toModel)
                .orElseThrow(MyResourceNotFoundException::new);

        return MyValueResponse.success(response);
    }

    @Override
    public MyValueResponse<ScheduleResponse> findById(Long scheduleId, Long stationResourceId) {
        ScheduleResponse response = scheduleUseCase
                .findById(DomainKey.of(scheduleId), DomainKey.of(stationResourceId))
                .map(responseMapper::toModel)
                .orElseThrow(MyResourceNotFoundException::new);

        return MyValueResponse.success(response);
    }
}
