package net.pool.station.core.features.schedule.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotFoundException;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.schedule.ScheduleUseCase;
import net.pool.station.core.features.schedule.controller.models.ScheduleRequest;
import net.pool.station.core.features.schedule.controller.models.ScheduleRequestMapper;
import net.pool.station.core.features.schedule.controller.models.ScheduleResponse;
import net.pool.station.core.features.schedule.controller.models.ScheduleResponseMapper;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ScheduleController implements ScheduleApi {
    ScheduleUseCase scheduleUseCase;

    ScheduleResponseMapper responseMapper;

    ScheduleRequestMapper requestMapper;

    @Override
    public MyValueResponse<?> update(Long scheduleId, ScheduleRequest request) {
        scheduleUseCase.update(DomainKey.of(scheduleId), requestMapper.toDto(request));

        return MyValueResponse.successNoData();
    }

    @Override
    public MyValueResponse<?> enable(Long scheduleId) {
        scheduleUseCase.enable(DomainKey.of(scheduleId));

        return MyValueResponse.successNoData();
    }

    @Override
    public MyValueResponse<?> disable(Long scheduleId) {
        scheduleUseCase.disable(DomainKey.of(scheduleId));

        return MyValueResponse.successNoData();
    }

    @Override
    public MyValueResponse<?> delete(Long scheduleId) {
        scheduleUseCase.delete(DomainKey.of(scheduleId));

        return MyValueResponse.successNoData();
    }
}
