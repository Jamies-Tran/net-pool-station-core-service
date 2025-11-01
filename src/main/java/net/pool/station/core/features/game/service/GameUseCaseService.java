package net.pool.station.core.features.game.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.enums.EGameStatus;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.game.Game;
import net.pool.station.core.domain.game.GameCriteria;
import net.pool.station.core.domain.game.GameUseCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GameUseCaseService implements GameUseCase {
    GameCommandService commandService;

    GameQueryService queryService;

    @Override
    @Transactional
    public void save(Game game) {
        commandService.save(game);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Game> findById(DomainKey<Long> gameId) {
        return queryService.findById(gameId.value());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Game> findAll(GameCriteria criteria, PageRequest pageRequest) {
        return queryService.findAll(criteria, pageRequest);
    }

    @Override
    @Transactional
    public void update(DomainKey<Long> gameId, Game game) {
        commandService.update(gameId.value(), game);
    }

    @Override
    @Transactional
    public void enable(DomainKey<Long> gameId) {
        commandService.updateStatus(gameId.value(), EGameStatus.ENABLE);
    }

    @Override
    @Transactional
    public void disable(DomainKey<Long> gameId) {
        commandService.updateStatus(gameId.value(), EGameStatus.DISABLE);
    }

    @Override
    @Transactional
    public void delete(DomainKey<Long> gameId) {
        commandService.delete(gameId.value());
    }
}
