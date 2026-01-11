package net.pool.station.core.features.match.participant.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyAuthenticationException;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotFoundException;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotValid;
import net.pool.station.core.bootstrap.enums.EMatchParticipantReadyStatus;
import net.pool.station.core.bootstrap.enums.EMatchParticipantStatus;
import net.pool.station.core.bootstrap.enums.EMatchParticipantType;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import net.pool.station.core.bootstrap.utils.MyRequestContext;
import net.pool.station.core.domain.login.info.LoginInfo;
import net.pool.station.core.domain.match.participant.MatchParticipant;
import net.pool.station.core.domain.match.participant.MatchParticipantCancel;
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
        emptyParticipant.setReadyStatusCode(EMatchParticipantReadyStatus.NOT_READY.getCode());
        emptyParticipant.setReadyStatusCode(EMatchParticipantReadyStatus.NOT_READY.getName());
        emptyParticipant.setStatusCode(EMatchParticipantStatus.FILLED.getCode());
        emptyParticipant.setStatusName(EMatchParticipantStatus.FILLED.getName());

        repository.save(emptyParticipant);

    }

    protected MatchParticipantCancel empty(Long matchParticipantId) {
        return repository.findById(matchParticipantId)
                .map(matchParticipant -> {
                    authorizeUpdate(matchParticipant);
                    if (MyObjectUtils.isEquals(matchParticipant.getTypeCode(), EMatchParticipantType.HOST.getCode())) {
                        return MatchParticipantCancel.builder()
                                .isCancel(true)
                                .matchMakingId(matchParticipant.getMatchMakingId())
                                .build();
                    }
                    matchParticipant.setAccountId(null);
                    matchParticipant.setTypeCode(null);
                    matchParticipant.setTypeName(null);
                    matchParticipant.setReadyStatusCode(null);
                    matchParticipant.setReadyStatusName(null);
                    matchParticipant.setStatusCode(EMatchParticipantStatus.EMPTY.getCode());
                    matchParticipant.setStatusName(EMatchParticipantStatus.EMPTY.getName());

                    repository.save(matchParticipant);

                    return MatchParticipantCancel.builder()
                            .isCancel(false)
                            .matchMakingId(matchParticipant.getMatchMakingId())
                            .build();
                })
                .orElseThrow(MyResourceNotFoundException::new);
    }

    private void authorizeUpdate(MatchParticipantEntity matchParticipant) {
        LoginInfo loginInfo = MyRequestContext.currentLoginInfo()
                .orElseThrow(MyAuthenticationException::new);
        if (MyObjectUtils.isNotEquals(loginInfo.accountId(), Long.valueOf(matchParticipant.getCreatedBy()))) {
            throw new MyAuthenticationException("Tài khoản của bạn không thể thực hiện thao tác này");
        }
    }
}
