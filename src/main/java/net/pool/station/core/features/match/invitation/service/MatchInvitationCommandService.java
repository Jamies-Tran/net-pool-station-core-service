package net.pool.station.core.features.match.invitation.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotFoundException;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotValid;
import net.pool.station.core.bootstrap.enums.EMatchInvitationStatus;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import net.pool.station.core.domain.match.invitation.MatchInvitation;
import net.pool.station.core.features.match.invitation.repository.database.MatchInvitationEntity;
import net.pool.station.core.features.match.invitation.repository.database.MatchInvitationMapper;
import net.pool.station.core.features.match.invitation.repository.database.MatchInvitationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MatchInvitationCommandService {
    MatchInvitationRepository repository;

    MatchInvitationMapper mapper;

    protected List<MatchInvitation> saveAll(Long matchInvitationId, List<MatchInvitation> matchInvitations) {
        List<MatchInvitationEntity> entityList = matchInvitations.stream()
                .map(m -> mapper.toEntity(m.withMatchMakingId(matchInvitationId)))
                .toList();
        List<MatchInvitationEntity> savedEntityList = repository.saveAll(entityList);

        return mapper.toDto(savedEntityList);
    }

    protected MatchInvitation updateStatus(Long matchInvitationId, EMatchInvitationStatus status) {
        return repository.findByMatchInvitationIdAndDeletedFalse(matchInvitationId)
                .map(e -> {
                    validate(e.getStatusCode(), status);
                    e.setStatusCode(status.getCode());
                    e.setStatusName(status.getName());
                    MatchInvitationEntity updatedEntity = repository.save(e);

                    return mapper.toDto(updatedEntity);
                })
                .orElseThrow(MyResourceNotFoundException::new);
    }

    private void validate(String currentStatusCode, EMatchInvitationStatus updateStatus) {
        switch (updateStatus) {
            case ACCEPTED, DENIED -> {
                if (MyObjectUtils.isNotEquals(currentStatusCode, EMatchInvitationStatus.SENT.getCode())) {
                    throw new MyResourceNotValid("Yêu cầu của bạn không hợp lệ");
                }
            }
            default -> {
                throw new MyResourceNotValid("Yêu cầu của bạn không hợp lệ");
            }
        }
    }
}
