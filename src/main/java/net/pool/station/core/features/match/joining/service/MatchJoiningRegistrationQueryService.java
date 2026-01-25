package net.pool.station.core.features.match.joining.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.match.joining.MatchJoiningRegistration;
import net.pool.station.core.domain.match.joining.MatchJoiningRegistrationCriteria;
import net.pool.station.core.features.match.joining.repository.database.MatchJoiningRegistrationMapper;
import net.pool.station.core.features.match.joining.repository.database.MatchJoiningRegistrationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MatchJoiningRegistrationQueryService {
    MatchJoiningRegistrationRepository repository;

    MatchJoiningRegistrationMapper mapper;

    protected Optional<MatchJoiningRegistration> findById(Long matchJoiningRegistrationId) {
        return repository.findById(matchJoiningRegistrationId)
                .map(mapper::toDto);
    }

    protected Page<MatchJoiningRegistration> findAll(MatchJoiningRegistrationCriteria criteria, PageRequest pageRequest) {
        return repository.findAll(criteria, pageRequest)
                .map(mapper::toDto);
    }

    protected Boolean existsByCreatedBy(Long matchJoiningRegistrationId, Long accountId) {
        return repository.existsByMatchJoiningRegistrationIdAndCreatedBy(matchJoiningRegistrationId,
                accountId.toString());
    }

    protected Boolean existsByMatchMakingCreatedBy(Long matchJoiningRegistrationId, Long accountId) {
        return repository.existsByMatchJoningRegistrationIdMatchMakingCreatedBy(matchJoiningRegistrationId,
                accountId.toString());
    }

    protected Boolean allowJoiningByMatchMakingId(Long matchMakingId) {
        return repository.allowJoiningByMatchMakingId(matchMakingId);
    }

    protected List<MatchJoiningRegistration> findAllByMatchMakingId(Long matchMakingId) {
        return mapper.toDto(repository.findAllByMatchMakingId(matchMakingId));
    }
}
