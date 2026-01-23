package net.pool.station.core.features.match.schedule.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.match.schedule.MatchSchedule;
import net.pool.station.core.domain.match.schedule.MatchScheduleId;
import net.pool.station.core.features.match.schedule.repository.database.MatchScheduleEntity;
import net.pool.station.core.features.match.schedule.repository.database.MatchScheduleMapper;
import net.pool.station.core.features.match.schedule.repository.database.MatchScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MatchScheduleCommandService {
    MatchScheduleRepository repository;

    MatchScheduleMapper mapper;

    protected void save(Long matchMakingSlotId, List<MatchSchedule> matchSchedules) {
        List<MatchScheduleEntity> entityList = matchSchedules
                .stream()
                .map(msc -> {
                    MatchScheduleId matchScheduleId = msc.id().withMatchMakingId(matchMakingSlotId);

                    return mapper.toEntity(msc.withId(matchScheduleId));
                })
                .toList();

        repository.saveAll(entityList);
    }

    protected void update(Long matchMakingSlotId, List<MatchSchedule> matchSchedules) {
        repository.deleteAllByMatchMakingId(matchMakingSlotId);
        save(matchMakingSlotId, matchSchedules);
    }
}
