package net.pool.station.core.features.match.making.resource.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.match.making.resource.MatchMakingResource;
import net.pool.station.core.domain.match.making.resource.MatchMakingResourceUseCase;
import net.pool.station.core.features.match.making.match.making.service.MatchMakingCommandService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MatchMakingResourceUseCaseService implements MatchMakingResourceUseCase {
    MatchMakingResourceCommandService commandService;

    MatchMakingResourceQueryService queryService;

    @Override
    @Transactional
    public void save(DomainKey<Long> matchMakingId, List<MatchMakingResource> matchMakingResources) {
        commandService.save(matchMakingId.value(),  matchMakingResources);
    }

    @Override
    @Transactional
    public void update(DomainKey<Long> matchMakingId, List<MatchMakingResource> matchMakingResources) {
        commandService.update(matchMakingId.value(),  matchMakingResources);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MatchMakingResource> findAllByMatchMakingId(DomainKey<Long> matchMakingId) {
        return queryService.findAllByMatchMakingId(matchMakingId.value());
    }
}
