package net.pool.station.core.features.game.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.domain.game.GameUseCase;
import net.pool.station.core.features.game.controller.models.GameRequest;
import net.pool.station.core.features.game.controller.models.GameRequestMapper;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GamesController implements GamesApi {

    GameUseCase gameUseCase;

    GameRequestMapper requestMapper;

    @Override
    public MyValueResponse<?> save(GameRequest request) {
        gameUseCase.save(requestMapper.toDto(request));

        return MyValueResponse.successNoData();
    }
}
