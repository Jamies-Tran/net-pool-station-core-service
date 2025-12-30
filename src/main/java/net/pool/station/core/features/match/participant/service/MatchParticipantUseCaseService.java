package net.pool.station.core.features.match.participant.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.match.participant.MatchParticipant;
import net.pool.station.core.domain.match.participant.MatchParticipantUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MatchParticipantUseCaseService implements MatchParticipantUseCase {
    MatchParticipantCommandService commandService;

    @Override
    @Transactional
    public void save(DomainKey<Long> matchMakingId, List<MatchParticipant> matchParticipants) {
        commandService.save(matchMakingId.value(), matchParticipants);
    }
}
