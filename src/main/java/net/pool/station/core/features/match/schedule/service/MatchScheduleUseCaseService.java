package net.pool.station.core.features.match.schedule.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.match.schedule.MatchSchedule;
import net.pool.station.core.domain.match.schedule.MatchScheduleUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MatchScheduleUseCaseService implements MatchScheduleUseCase {
    MatchScheduleCommandService commandService;

    MatchScheduleQueryService queryService;

    @Override
    @Transactional
    public void save(DomainKey<Long> matchMakingSlotId, List<MatchSchedule> matchSchedules) {
        commandService.save(matchMakingSlotId.value(), matchSchedules);
    }

    @Override
    @Transactional
    public void update(DomainKey<Long> matchMakingSlotId, List<MatchSchedule> matchSchedules) {
        commandService.update(matchMakingSlotId.value(), matchSchedules);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MatchSchedule> findAllByMatchMakingId(DomainKey<Long> matchMakingId) {
        return queryService.findAllByMatchMakingId(matchMakingId.value());
    }
}
