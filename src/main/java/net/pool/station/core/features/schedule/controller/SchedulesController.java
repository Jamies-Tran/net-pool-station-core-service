package net.pool.station.core.features.schedule.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.domain.schedule.Schedule;
import net.pool.station.core.domain.schedule.ScheduleUseCase;
import net.pool.station.core.features.schedule.controller.models.ScheduleListRequest;
import net.pool.station.core.features.schedule.controller.models.ScheduleRequest;
import net.pool.station.core.features.schedule.controller.models.ScheduleRequestMapper;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SchedulesController implements SchedulesApi {
    ScheduleUseCase scheduleUseCase;

    ScheduleRequestMapper requestMapper;

    @Override
    public MyValueResponse<?> save(ScheduleRequest request) {
        scheduleUseCase.save(requestMapper.toDto(request));

        return MyValueResponse.successNoData();
    }

    @Override
    public MyValueResponse<?> save(ScheduleListRequest request) {
        List<Schedule> schedules = Schedule.from(request.stationId(), request.dateFrom(),
                request.dateTo());
        Schedule.TimeSlotConfig timeSlotConfig = Schedule.TimeSlotConfig.builder()
                .from(request.timeSlotConfig().from())
                .to(request.timeSlotConfig().to())
                .interval(request.timeSlotConfig().interval())
                .build();
        scheduleUseCase.save(schedules, timeSlotConfig);

        return MyValueResponse.successNoData();
    }
}
