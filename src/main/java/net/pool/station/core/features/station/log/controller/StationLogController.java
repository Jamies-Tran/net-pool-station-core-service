package net.pool.station.core.features.station.log.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.rest.response.MyPageResponse;
import net.pool.station.core.bootstrap.rest.response.MySorter;
import net.pool.station.core.domain.station.log.StationLogCriteria;
import net.pool.station.core.domain.station.log.StationLogUseCase;
import net.pool.station.core.features.station.log.controller.models.StationLogResponse;
import net.pool.station.core.features.station.log.controller.models.StationLogResponseMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StationLogController implements StationLogApi {
    StationLogUseCase stationLogUseCase;

    StationLogResponseMapper responseMapper;

    @Override
    public MyPageResponse<StationLogResponse> findAll(
            Long stationId,
            String search,
            List<LocalDateTime> timeRange,
            List<String> logTypeCodes,
            String sorter, Integer current, Integer pageSize
    ) {
        StationLogCriteria criteria = StationLogCriteria.of(search, stationId, timeRange, logTypeCodes);
        PageRequest pageRequest = PageRequest.of(current, pageSize, MySorter.of(sorter));
        Page<StationLogResponse> responses = stationLogUseCase.findAll(criteria, pageRequest)
                .map(responseMapper::toModel);

        return MyPageResponse.success(responses);
    }
}
