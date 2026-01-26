package net.pool.station.core.features.game.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.game.Game;
import net.pool.station.core.domain.game.GameCriteria;
import net.pool.station.core.features.game.repository.database.GameEntityMapper;
import net.pool.station.core.features.game.repository.database.GameRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GameQueryService {
    GameRepository repository;

    GameEntityMapper mapper;

    protected Optional<Game> findById(Long gameId) {
        return repository.findByGameIdAndDeletedFalse(gameId)
                .map(mapper::toDto);
    }

    protected Page<Game> findAll(GameCriteria criteria, PageRequest pageRequest) {
        return repository.findAll(criteria, pageRequest)
                .map(mapper::toDto);
    }

    protected List<Game> findAllByGameIdIn(List<Long> gameIds) {
        return mapper.toDto(repository.findAllByGameIdIn(gameIds));
    }
}
