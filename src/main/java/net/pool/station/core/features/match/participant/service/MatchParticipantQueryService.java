package net.pool.station.core.features.match.participant.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.enums.EMatchParticipantStatus;
import net.pool.station.core.domain.match.participant.MatchParticipant;
import net.pool.station.core.domain.match.participant.MatchParticipantCriteria;
import net.pool.station.core.features.match.participant.repository.database.MatchParticipantEntity;
import net.pool.station.core.features.match.participant.repository.database.MatchParticipantMapper;
import net.pool.station.core.features.match.participant.repository.database.MatchParticipantRepository;
import org.mapstruct.ap.shaded.freemarker.core.ReturnInstruction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MatchParticipantQueryService {
    MatchParticipantRepository repository;

    MatchParticipantMapper mapper;

    protected List<MatchParticipant> findAllByMatchMakingId(Long matchMakingId) {
        List<MatchParticipantEntity> entityList = repository.findAllByMatchMakingId(matchMakingId);

        return mapper.toDto(entityList);
    }

    protected Page<MatchParticipant> findAll(MatchParticipantCriteria criteria, PageRequest pageRequest) {
        return repository.findAll(criteria, pageRequest)
                .map(mapper::toDto);
    }

    protected Optional<MatchParticipant> findById(Long matchParticipantId) {
        return repository.findById(matchParticipantId)
                .map(mapper::toDto);
    }

    protected Boolean allowByAccountId(Long matchParticipantId, Long accountId) {
        return repository.allowsByAccountId(matchParticipantId, accountId);
    }
}
