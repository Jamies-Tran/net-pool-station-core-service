package net.pool.station.core.features.schedule.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.rest.response.MyListResponse;
import net.pool.station.core.bootstrap.rest.response.MyPageResponse;
import net.pool.station.core.bootstrap.rest.response.MySorter;
import net.pool.station.core.domain.schedule.Schedule;
import net.pool.station.core.domain.schedule.ScheduleCriteria;
import net.pool.station.core.domain.schedule.ScheduleUseCase;
import net.pool.station.core.features.schedule.controller.models.ScheduleCountListRequest;
import net.pool.station.core.features.schedule.controller.models.ScheduleResponse;
import net.pool.station.core.features.schedule.controller.models.ScheduleResponseMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SchedulesPubController implements SchedulesPubApi {
    ScheduleUseCase scheduleUseCase;

    ScheduleResponseMapper responseMapper;

    @Override
    public MyPageResponse<ScheduleResponse> findAllByStation(
            Long stationId,
            List<LocalDate> dateRange,
            List<String> statusCodes,
            String sorter, Integer current, Integer pageSize
    ) {
        ScheduleCriteria criteria = ScheduleCriteria.ofStation(stationId, dateRange, statusCodes);
        PageRequest pageRequest = PageRequest.of(current, pageSize, MySorter.of(sorter));
        Page<ScheduleResponse> responses = scheduleUseCase.findAllByStation(criteria, pageRequest)
                .map(responseMapper::toModel);

        return MyPageResponse.success(responses);
    }

    @Override
    public MyPageResponse<ScheduleResponse> findAllByStationResource(
            Long stationResourceId,
            List<LocalDate> dateRange,
            List<String> statusCodes,
            String sorter, Integer current, Integer pageSize
    ) {

        ScheduleCriteria criteria = ScheduleCriteria.ofStationResource(stationResourceId, dateRange, statusCodes);
        PageRequest pageRequest = PageRequest.of(current, pageSize);
        Page<ScheduleResponse> responses = scheduleUseCase.findAllByStationResource(criteria, pageRequest)
                .map(responseMapper::toModel);

        return MyPageResponse.success(responses);
    }

    @Override
    public MyPageResponse<ScheduleResponse> findAllByStationSpace(
            Long stationSpaceId,
            List<LocalDate> dateRange,
            List<String> statusCodes,
            String sorter, Integer current, Integer pageSize
    ) {
        ScheduleCriteria criteria = ScheduleCriteria.ofStationSpace(stationSpaceId, dateRange, statusCodes);
        PageRequest pageRequest = PageRequest.of(current, pageSize);
        Page<ScheduleResponse> responses = scheduleUseCase.findAllByStationSpace(criteria, pageRequest)
                .map(responseMapper::toModel);

        return MyPageResponse.success(responses);
    }

    @Override
    public MyListResponse<ScheduleResponse> findAllByStationIdAndDateFromAndDateCount(ScheduleCountListRequest request) {
        List<LocalTime> time = Stream.of(request.begin(), request.end())
                .sorted()
                .toList();
        List<Schedule> schedules = scheduleUseCase
                .findAllByStationIdAndDateFromAndDateCount(request.stationId(), request.dateFrom(), request.stationResourceId(), time, request.dateCount());
        return MyListResponse.success(responseMapper.toModel(schedules));
    }
}
