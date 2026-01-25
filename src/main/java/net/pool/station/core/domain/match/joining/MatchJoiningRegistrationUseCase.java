package net.pool.station.core.domain.match.joining;

import net.pool.station.core.domain.DomainKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

public interface MatchJoiningRegistrationUseCase {
    void save(MatchJoiningRegistration matchJoiningRegistration);

    Optional<MatchJoiningRegistration> findById(DomainKey<Long> matchJoiningRegistrationId);

    Page<MatchJoiningRegistration> findAll(MatchJoiningRegistrationCriteria criteria, PageRequest pageRequest);

    void update(DomainKey<Long> matchJoiningRegistrationId, MatchJoiningRegistration matchJoiningRegistration);

    void accept(DomainKey<Long> matchJoiningRegistrationId);

    void deny(DomainKey<Long> matchJoiningRegistrationId);

    void cancel(DomainKey<Long> matchJoiningRegistrationId);

    List<MatchJoiningRegistration> findAllByMatchMakingId(DomainKey<Long> matchMakingId);
}
