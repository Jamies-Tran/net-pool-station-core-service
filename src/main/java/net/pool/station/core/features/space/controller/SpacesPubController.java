package net.pool.station.core.features.space.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.rest.response.MyPageResponse;
import net.pool.station.core.bootstrap.rest.response.MySorter;
import net.pool.station.core.domain.space.SpaceCriteria;
import net.pool.station.core.domain.space.SpaceUseCase;
import net.pool.station.core.features.space.controller.models.SpaceResponse;
import net.pool.station.core.features.space.controller.models.SpaceResponseMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SpacesPubController implements SpacesPubApi {
    SpaceUseCase spaceUseCase;

    SpaceResponseMapper responseMapper;

    @Override
    public MyPageResponse<SpaceResponse> findAll(
            String search,
            Long stationId,
            List<String> typeCodes,
            List<String> statusCodes,
            String sorter, Integer current, Integer pageSize
    ) {
        SpaceCriteria criteria = SpaceCriteria.of(search, stationId, typeCodes, statusCodes);
        PageRequest pageRequest = PageRequest.of(current, pageSize, MySorter.of(sorter));
        Page<SpaceResponse> responses = spaceUseCase.findAll(criteria, pageRequest)
                .map(responseMapper::toModel);

        return MyPageResponse.success(responses);
    }
}
