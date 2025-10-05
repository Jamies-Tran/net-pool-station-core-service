package net.pool.station.core.features.schedule.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.rest.response.MyPageResponse;
import net.pool.station.core.bootstrap.rest.response.MySorter;
import net.pool.station.core.domain.schedule.ScheduleCriteria;
import net.pool.station.core.domain.schedule.ScheduleUseCase;
import net.pool.station.core.features.schedule.controller.models.ScheduleResponse;
import net.pool.station.core.features.schedule.controller.models.ScheduleResponseMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SchedulesPubController implements SchedulesPubApi {
    ScheduleUseCase scheduleUseCase;

    ScheduleResponseMapper responseMapper;

    @Override
    public MyPageResponse<ScheduleResponse> findAll(
            Long stationId,
            List<LocalDate> dateRange,
            List<String> statusCodes,
            String sorter, Integer current, Integer pageSize
    ) {
        ScheduleCriteria criteria = ScheduleCriteria.of(stationId, dateRange, statusCodes);
        PageRequest pageRequest = PageRequest.of(current, pageSize, MySorter.of(sorter));
        Page<ScheduleResponse> responses = scheduleUseCase.findAll(criteria, pageRequest)
                .map(responseMapper::toModel);

        return MyPageResponse.success(responses);
    }
}
