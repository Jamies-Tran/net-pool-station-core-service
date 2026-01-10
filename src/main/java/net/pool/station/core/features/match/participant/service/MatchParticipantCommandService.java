package net.pool.station.core.features.match.participant.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotFoundException;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotValid;
import net.pool.station.core.bootstrap.enums.EMatchParticipantStatus;
import net.pool.station.core.bootstrap.enums.EMatchParticipantType;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import net.pool.station.core.domain.match.participant.MatchParticipant;
import net.pool.station.core.features.match.participant.repository.database.MatchParticipantEntity;
import net.pool.station.core.features.match.participant.repository.database.MatchParticipantMapper;
import net.pool.station.core.features.match.participant.repository.database.MatchParticipantRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    protected void update(Long matchMakingId, Long accountId) {
        List<MatchParticipantEntity> emptyList = repository.findAllByMatchMakingIdAndStatusCode(matchMakingId,
                EMatchParticipantStatus.EMPTY.getCode());
        MatchParticipantEntity emptyParticipant = emptyList.stream()
                .findAny()
                .orElseThrow(() -> new MyResourceNotFoundException("Phòng sếp trận đã đủ người"));
        emptyParticipant.setAccountId(accountId);
        emptyParticipant.setTypeCode(EMatchParticipantType.MEMBER.getCode());
        emptyParticipant.setTypeName(EMatchParticipantType.MEMBER.getName());
        emptyParticipant.setStatusCode(EMatchParticipantStatus.FILLED.getCode());
        emptyParticipant.setStatusName(EMatchParticipantStatus.FILLED.getName());

        repository.save(emptyParticipant);

    }

    protected void empty(Long matchParticipantId) {
        repository.findById(matchParticipantId)
                .ifPresentOrElse(
                        matchParticipant -> {
                            List<MatchParticipantEntity> savedList = new ArrayList<>();
                            matchParticipant.setAccountId(null);
                            matchParticipant.setTypeCode(null);
                            matchParticipant.setTypeName(null);
                            matchParticipant.setStatusCode(EMatchParticipantStatus.EMPTY.getCode());
                            matchParticipant.setStatusName(EMatchParticipantStatus.EMPTY.getName());
                            savedList.add(matchParticipant);
                            if (MyObjectUtils.isEquals(matchParticipant.getTypeCode(),
                                    EMatchParticipantType.HOST.getCode())) {
                                Optional<MatchParticipantEntity> participantOptional = repository
                                        .findAllByMatchMakingIdAndStatusCode(matchParticipant.getMatchParticipantId(),
                                                EMatchParticipantStatus.FILLED.getCode())
                                        .stream()
                                        .filter(m -> MyObjectUtils
                                                .isNotEquals(m.getMatchParticipantId(), matchParticipant
                                                        .getMatchParticipantId()))
                                        .findAny();
                                if (participantOptional.isPresent()) {
                                    MatchParticipantEntity participant = participantOptional.get();
                                    participant.setTypeCode(EMatchParticipantType.HOST.getCode());
                                    participant.setTypeName(EMatchParticipantType.HOST.getName());
                                    savedList.add(participant);
                                } else {
                                    throw new MyResourceNotValid("Không còn thành viên nào trong phòng, chỉ có thể hủy.");
                                }
                            }

                            repository.saveAll(savedList);
                        },
                        MyResourceNotFoundException::new
                );
    }
}
