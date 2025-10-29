package net.pool.station.core.features.station.resource.resource.controller.api;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.rest.response.MyPageResponse;
import net.pool.station.core.bootstrap.rest.response.MySorter;
import net.pool.station.core.domain.station.resource.StationResourceCriteria;
import net.pool.station.core.domain.station.resource.StationResourceUseCase;
import net.pool.station.core.features.station.resource.resource.controller.api.models.StationResourceResponse;
import net.pool.station.core.features.station.resource.resource.controller.api.models.StationResourceResponseMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StationResourcesPubController implements StationResourcesPubApi{
    StationResourceUseCase stationResourceUseCase;

    StationResourceResponseMapper responseMapper;

    @Override
    public MyPageResponse<?> findAll(
            String search,
            Long areaId,
            List<String> typeCodes,
            List<String> statusCodes,
            String sorter, Integer current, Integer pageSize
    ) {
        StationResourceCriteria criteria = StationResourceCriteria.of(search, areaId, typeCodes, statusCodes);
        PageRequest pageRequest = PageRequest.of(current, pageSize, MySorter.of(sorter));
        Page<StationResourceResponse> responses = stationResourceUseCase.findAll(criteria, pageRequest)
                .map(responseMapper::toModel);

        return MyPageResponse.success(responses);
    }
}
