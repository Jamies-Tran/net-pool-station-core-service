package net.pool.station.core.features.station.space.space.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.rest.response.MyPageResponse;
import net.pool.station.core.bootstrap.rest.response.MySorter;
import net.pool.station.core.domain.station.space.StationSpaceCriteria;
import net.pool.station.core.domain.station.space.StationSpaceUseCase;
import net.pool.station.core.features.station.space.space.controller.models.StationSpaceResponse;
import net.pool.station.core.features.station.space.space.controller.models.StationSpaceResponseMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StationSpacesPubController implements StationSpacesPubApi {
    StationSpaceUseCase stationSpaceUseCase;

    StationSpaceResponseMapper responseMapper;

    @Override
    public MyPageResponse<StationSpaceResponse> findAll(
            String search,
            Long stationId,
            List<String> typeCodes,
            List<String> statusCodes,
            String sorter, Integer current, Integer pageSize
    ) {
        StationSpaceCriteria criteria = StationSpaceCriteria
                .of(search, stationId, typeCodes, statusCodes);
        PageRequest pageRequest = PageRequest.of(current, pageSize, MySorter.of(sorter));
        Page<StationSpaceResponse> responses = stationSpaceUseCase.findAll(criteria, pageRequest)
                .map(responseMapper::toModel);

        return MyPageResponse.success(responses);
    }
}
