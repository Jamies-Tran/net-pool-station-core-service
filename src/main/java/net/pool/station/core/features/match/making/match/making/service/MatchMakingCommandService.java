package net.pool.station.core.features.match.making.match.making.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotFoundException;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotValid;
import net.pool.station.core.bootstrap.enums.EMatchMakingStatus;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import net.pool.station.core.domain.match.making.MatchMaking;
import net.pool.station.core.features.match.making.match.making.repository.database.MatchMakingEntity;
import net.pool.station.core.features.match.making.match.making.repository.database.MatchMakingMapper;
import net.pool.station.core.features.match.making.match.making.repository.database.MatchMakingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MatchMakingCommandService {
    MatchMakingRepository repository;

    MatchMakingMapper mapper;

    protected MatchMaking save(MatchMaking matchMaking) {
        MatchMakingEntity newMatchMaking = mapper.toEntity(matchMaking);
        MatchMakingEntity savedMatchMaking = repository.save(newMatchMaking);

        return mapper.toDto(savedMatchMaking);
    }

    protected MatchMaking update(Long matchMakingId, MatchMaking matchMaking) {
        MatchMakingEntity foundMatchMaking = repository.findByMatchMakingIdAndDeletedFalse(matchMakingId)
                .orElseThrow(MyResourceNotFoundException::new);
        mapper.update(foundMatchMaking, matchMaking);
        MatchMakingEntity savedMatchMaking = repository.save(foundMatchMaking);

        return mapper.toDto(savedMatchMaking);
    }

    protected void delete(Long matchMakingId) {
        MatchMakingEntity foundMatchMaking = repository.findByMatchMakingIdAndDeletedFalse(matchMakingId)
                .orElseThrow(MyResourceNotFoundException::new);
        foundMatchMaking.setDeleted(true);

        repository.save(foundMatchMaking);
    }

    protected MatchMaking updateStatus(Long matchMakingId, EMatchMakingStatus status) {
        return repository.findByMatchMakingIdAndDeletedFalse(matchMakingId)
                .map(
                        foundMatchMaking -> {
                            validateUpdateStatus(foundMatchMaking.getStatusCode(), status);
                            foundMatchMaking.setStatusCode(status.getCode());
                            foundMatchMaking.setStatusName(status.getName());

                            return mapper.toDto(repository.save(foundMatchMaking));
                        }
                )
                .orElseThrow(MyResourceNotFoundException::new);
    }

    protected MatchMaking updateStatus(Long matchMakingId, EMatchMakingStatus status,
                                       LocalDateTime paidDepositAt) {
        return repository.findByMatchMakingIdAndDeletedFalse(matchMakingId)
                .map(
                        foundMatchMaking -> {
                            validateUpdateStatus(foundMatchMaking.getStatusCode(), status);
                            foundMatchMaking.setStatusCode(status.getCode());
                            foundMatchMaking.setStatusName(status.getName());
                            foundMatchMaking.setPaidDepositAt(paidDepositAt);
                            if (MyObjectUtils.isEquals(status, EMatchMakingStatus.PENDING)) {
                                foundMatchMaking.setProcessAt(LocalDate.now());
                            }

                            return mapper.toDto(repository.save(foundMatchMaking));
                        }
                )
                .orElseThrow(MyResourceNotFoundException::new);
    }

    protected void handleExpiredJob(Long matchMakingId) {
        repository.findByMatchMakingIdAndDeletedFalse(matchMakingId)
                .ifPresentOrElse(
                        foundMatchMaking -> {
                            if (LocalDate.now().isAfter(foundMatchMaking.getExpiredAt())) {
                                switch (EMatchMakingStatus.valueOf(foundMatchMaking.getStatusCode())) {
                                    case PENDING, DRAFT -> {
                                        foundMatchMaking.setStatusCode(EMatchMakingStatus.CANCEL.getCode());
                                        foundMatchMaking.setStatusName(EMatchMakingStatus.CANCEL.getName());
                                        repository.save(foundMatchMaking);
                                    }
                                    case STARTED -> {
                                        foundMatchMaking.setStatusCode(EMatchMakingStatus.FINISHED.getCode());
                                        foundMatchMaking.setStatusName(EMatchMakingStatus.FINISHED.getName());
                                        repository.save(foundMatchMaking);
                                    }
                                }
                            }
                        },
                        () -> {
                            log.error("[MatchMakingCommandService.handleExpiredJob(...)] message: Not found");
                        }
                );
    }

    private void validateUpdateStatus(String statusCode, EMatchMakingStatus status) {
        switch (status) {
            case PENDING -> {
                if (MyObjectUtils.isNotEquals(statusCode, EMatchMakingStatus.DRAFT.getCode())) {
                    throw new MyResourceNotValid();
                }
            }
            case STARTED, CANCEL -> {
                if (MyObjectUtils.isNotEquals(statusCode, EMatchMakingStatus.PENDING.getCode())) {
                    throw new MyResourceNotValid();
                }
            }
            case FINISHED -> {
                if (MyObjectUtils.isNotEquals(statusCode, EMatchMakingStatus.STARTED.getCode())) {
                    throw new MyResourceNotValid();
                }
            }
        }
    }
}
