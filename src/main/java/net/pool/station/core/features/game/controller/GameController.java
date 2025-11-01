package net.pool.station.core.features.game.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.game.GameUseCase;
import net.pool.station.core.features.game.controller.models.GameRequest;
import net.pool.station.core.features.game.controller.models.GameRequestMapper;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GameController implements GameApi {

    GameUseCase gameUseCase;

    GameRequestMapper requestMapper;

    @Override
    public MyValueResponse<?> update(Long gameId, GameRequest request) {
        gameUseCase.update(DomainKey.of(gameId), requestMapper.toDto(request));

        return MyValueResponse.successNoData();
    }

    @Override
    public MyValueResponse<?> enable(Long gameId) {
        gameUseCase.enable(DomainKey.of(gameId));

        return MyValueResponse.successNoData();
    }

    @Override
    public MyValueResponse<?> disable(Long gameId) {
        gameUseCase.disable(DomainKey.of(gameId));

        return MyValueResponse.successNoData();
    }

    @Override
    public MyValueResponse<?> delete(Long gameId) {
        gameUseCase.delete(DomainKey.of(gameId));

        return MyValueResponse.successNoData();
    }
}
