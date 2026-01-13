package net.pool.station.core.features.match.joining.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotFoundException;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotValid;
import net.pool.station.core.bootstrap.enums.EMatchJoiningRegistrationStatus;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import net.pool.station.core.domain.match.joining.MatchJoiningRegistration;
import net.pool.station.core.features.match.joining.repository.database.MatchJoiningRegistrationEntity;
import net.pool.station.core.features.match.joining.repository.database.MatchJoiningRegistrationMapper;
import net.pool.station.core.features.match.joining.repository.database.MatchJoiningRegistrationRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MatchJoiningRegistrationCommandService {
    MatchJoiningRegistrationRepository repository;

    MatchJoiningRegistrationMapper mapper;

    protected MatchJoiningRegistration save(MatchJoiningRegistration registration) {
        MatchJoiningRegistrationEntity entity = repository.save(mapper.toEntity(registration));

        return mapper.toDto(entity);
    }

    protected MatchJoiningRegistration update(Long matchJoiningRegistrationId,
                                              MatchJoiningRegistration matchJoiningRegistration) {
        return repository.findById(matchJoiningRegistrationId)
                .map(m -> {
                    mapper.update(m, matchJoiningRegistration);

                    return mapper.toDto(repository.save(m));
                })
                .orElseThrow(MyResourceNotFoundException::new);
    }

    protected MatchJoiningRegistration updateStatus(Long matchJoiningRegistrationId, EMatchJoiningRegistrationStatus status) {
        return repository.findById(matchJoiningRegistrationId)
                .map(m -> {
                    validate(m.getStatusCode(), status);
                    m.setStatusCode(status.getCode());
                    m.setStatusName(status.getName());

                    return mapper.toDto(repository.save(m));
                })
                .orElseThrow(MyResourceNotFoundException::new);
    }

    private void validate(String sourceStatusCode, EMatchJoiningRegistrationStatus updateStatus) {
        switch (updateStatus) {
            case ACCEPT, DENY, CANCEL -> {
                if (MyObjectUtils.isNotEquals(sourceStatusCode,
                        EMatchJoiningRegistrationStatus.DELIVERED.getCode())) {
                    throw new MyResourceNotValid("Thư mời không thể thao tác vào lúc này.");
                }
            }
            default -> {
                throw new MyResourceNotValid();
            }
        }
    }
}
