package net.pool.station.core.features.game.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.rest.response.MyPageResponse;
import net.pool.station.core.bootstrap.rest.response.MySorter;
import net.pool.station.core.domain.game.GameCriteria;
import net.pool.station.core.domain.game.GameUseCase;
import net.pool.station.core.features.game.controller.models.GameResponse;
import net.pool.station.core.features.game.controller.models.GameResponseMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GamesPubController implements GamesPubApi {

    GameUseCase gameUseCase;

    GameResponseMapper responseMapper;

    @Override
    public MyPageResponse<GameResponse> findAll(
            Long stationSpaceId,
            String search,
            List<String> genreCodes,
            List<String> statusCodes,
            String sorter, Integer current, Integer pageSize
    ) {
        GameCriteria criteria = GameCriteria.of(search, stationSpaceId, genreCodes, statusCodes);
        PageRequest pageRequest = PageRequest.of(current, pageSize, MySorter.of(sorter));
        Page<GameResponse> responses = gameUseCase.findAll(criteria, pageRequest)
                .map(responseMapper::toModel);

        return MyPageResponse.success(responses);
    }
}
