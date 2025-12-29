package net.pool.station.core.features.match.making.slot.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.match.making.slot.MatchMakingSlot;
import net.pool.station.core.domain.match.making.slot.MatchMakingSlotId;
import net.pool.station.core.features.match.making.slot.respository.database.MatchMakingSlotEntity;
import net.pool.station.core.features.match.making.slot.respository.database.MatchMakingSlotMapper;
import net.pool.station.core.features.match.making.slot.respository.database.MatchMakingSlotRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MatchMakingSlotCommandService {
    MatchMakingSlotRepository repository;

    MatchMakingSlotMapper mapper;

    protected void save(Long matchMakingId, List<MatchMakingSlot> matchMakingSlots) {
        List<MatchMakingSlotEntity> newMatchMakings = matchMakingSlots.stream()
                .map(matchMaking -> {
                    MatchMakingSlotId matchMakingSlotId = matchMaking.id()
                            .withMatchMakingId(matchMakingId);

                    return mapper.toEntity(matchMaking.withId(matchMakingSlotId));
                })
                .toList();

        repository.saveAll(newMatchMakings);
    }

    protected void update(Long matchMakingId, List<MatchMakingSlot> matchMakingSlots) {
        repository.deleteAllByMatchMakingId(matchMakingId);
        List<MatchMakingSlotEntity> newMatchMaking = matchMakingSlots.stream()
                .map(matchMaking -> {
                    MatchMakingSlotId matchMakingSlotId = matchMaking.id()
                            .withMatchMakingId(matchMakingId);

                    return mapper.toEntity(matchMaking.withId(matchMakingSlotId));
                })
                .toList();

        repository.saveAll(newMatchMaking);
    }
}
