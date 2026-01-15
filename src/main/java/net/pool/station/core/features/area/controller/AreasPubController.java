package net.pool.station.core.features.area.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.rest.response.MyPageResponse;
import net.pool.station.core.bootstrap.rest.response.MySorter;
import net.pool.station.core.domain.area.AreaCriteria;
import net.pool.station.core.domain.area.AreaUseCase;
import net.pool.station.core.features.area.controller.models.AreaResponse;
import net.pool.station.core.features.area.controller.models.AreaResponseMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AreasPubController implements AreasPubApi {
    AreaUseCase areaUseCase;

    AreaResponseMapper responseMapper;

    @Override
    public MyPageResponse<AreaResponse> findAll(
            String search,
            Long stationSpaceId,
            Long spaceId,
            List<String> statusCodes,
            List<String> typeCodes,
            String sorter, Integer current, Integer pageSize
    ) {
        AreaCriteria criteria = AreaCriteria.of(search, stationSpaceId, spaceId, statusCodes, typeCodes);
        PageRequest pageRequest = PageRequest.of(current, pageSize, MySorter.of(sorter));
        Page<AreaResponse> responses = areaUseCase.findAll(criteria, pageRequest)
                .map(responseMapper::toModel);

        return MyPageResponse.success(responses);
    }
}
