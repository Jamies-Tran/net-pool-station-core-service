package net.pool.station.core.features.game.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotFoundException;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.game.GameUseCase;
import net.pool.station.core.features.game.controller.models.GameResponse;
import net.pool.station.core.features.game.controller.models.GameResponseMapper;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GamePubController implements GamePubApi {

    GameUseCase gameUseCase;

    GameResponseMapper responseMapper;

    @Override
    public MyValueResponse<GameResponse> findById(Long gameId) {
        GameResponse response = gameUseCase.findById(DomainKey.of(gameId))
                .map(responseMapper::toModel)
                .orElseThrow(MyResourceNotFoundException::new);

        return MyValueResponse.success(response);
    }
}
