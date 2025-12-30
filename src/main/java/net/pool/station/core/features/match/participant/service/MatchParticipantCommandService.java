package net.pool.station.core.features.match.participant.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.match.participant.MatchParticipant;
import net.pool.station.core.features.match.participant.repository.database.MatchParticipantMapper;
import net.pool.station.core.features.match.participant.repository.database.MatchParticipantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MatchParticipantCommandService {
    MatchParticipantRepository repository;

    MatchParticipantMapper mapper;

    protected void save(Long matchMakingId, List<MatchParticipant> matchParticipants) {
        matchParticipants = matchParticipants.stream()
                .map(matchParticipant -> matchParticipant.withMatchMakingId(matchMakingId))
                .toList();

        repository.saveAll(mapper.toEntity(matchParticipants));
    }
}
