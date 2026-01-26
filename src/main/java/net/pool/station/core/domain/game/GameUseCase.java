package net.pool.station.core.domain.game;

import net.pool.station.core.domain.DomainKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

public interface GameUseCase {
    void save(Game game);

    Optional<Game> findById(DomainKey<Long> gameId);

    Page<Game> findAll(GameCriteria criteria, PageRequest pageRequest);

    List<Game> findAllByIdIn(List<Long> gameIds);

    void update(DomainKey<Long> gameId, Game game);

    void enable(DomainKey<Long> gameId);

    void disable(DomainKey<Long> gameId);

    void delete(DomainKey<Long> gameId);
}
