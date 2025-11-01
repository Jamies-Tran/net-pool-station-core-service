package net.pool.station.core.features.game.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotFoundException;
import net.pool.station.core.bootstrap.enums.EGameStatus;
import net.pool.station.core.domain.game.Game;
import net.pool.station.core.features.game.repository.database.GameEntity;
import net.pool.station.core.features.game.repository.database.GameEntityMapper;
import net.pool.station.core.features.game.repository.database.GameRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GameCommandService {
    GameRepository repository;

    GameEntityMapper mapper;

    protected void save(Game game) {
        String stationCode = repository.findStationCodeByStationSpaceId(game.stationSpaceId())
                .map(code -> code.split("ST_")[1])
                .orElseThrow(MyResourceNotFoundException::new);
        String gameCode = "G_".concat(stationCode);
        GameEntity newGame = mapper.toEntity(game);
        newGame.setGameCode(gameCode);

        repository.save(newGame);
    }

    protected void update(Long gameId, Game game) {
        repository.findByGameIdAndDeletedFalse(gameId)
                .ifPresentOrElse(
                        foundEntity -> {
                            mapper.update(foundEntity, game);
                            repository.save(foundEntity);
                        },
                        () -> {
                            throw new MyResourceNotFoundException();
                        }
                );
    }

    protected void updateStatus(Long gameId, EGameStatus status) {
        repository.findByGameIdAndDeletedFalse(gameId)
                .ifPresentOrElse(
                        foundEntity -> {
                            foundEntity.setStatusCode(status.getCode());
                            foundEntity.setStatusName(status.getName());
                            repository.save(foundEntity);
                        },
                        () -> {
                            throw new MyResourceNotFoundException();
                        }
                );
    }

    protected void delete(Long gameId) {
        repository.findByGameIdAndDeletedFalse(gameId)
                .ifPresentOrElse(
                        foundEntity -> {
                            foundEntity.setDeleted(true);
                            repository.save(foundEntity);
                        },
                        () -> {
                            throw new MyResourceNotFoundException();
                        }
                );
    }
}
