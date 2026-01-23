package net.pool.station.core.features.match.schedule.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.match.schedule.MatchSchedule;
import net.pool.station.core.features.match.schedule.repository.database.MatchScheduleMapper;
import net.pool.station.core.features.match.schedule.repository.database.MatchScheduleRepository;
import net.pool.station.core.features.match.schedule.repository.database.dao.MatchScheduleDaoMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MatchScheduleQueryService {
    MatchScheduleRepository repository;

    MatchScheduleDaoMapper mapper;

    protected List<MatchSchedule> findAllByMatchMakingId(Long matchMakingId) {
        return mapper.toDto(repository.findAllByMatchMakingId(matchMakingId));
    }
}
