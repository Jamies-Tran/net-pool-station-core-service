package net.pool.station.core.features.match.making.slot.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.match.making.slot.MatchMakingSlot;
import net.pool.station.core.domain.match.making.slot.MatchMakingSlotUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MatchMakingSlotUseCaseService implements MatchMakingSlotUseCase {
    MatchMakingSlotCommandService commandService;

    MatchMakingSlotQueryService queryService;

    @Override
    @Transactional
    public void save(DomainKey<Long> matchMakingId, List<MatchMakingSlot> matchMakingSlots) {
        commandService.save(matchMakingId.value(), matchMakingSlots);
    }

    @Override
    @Transactional
    public void update(DomainKey<Long> matchMakingId, List<MatchMakingSlot> matchMakingSlots) {
        commandService.update(matchMakingId.value(), matchMakingSlots);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MatchMakingSlot> findAllByMatchMakingId(DomainKey<Long> matchMakingId) {
        return queryService.findAllByMatchMakingId(matchMakingId.value());
    }
}
