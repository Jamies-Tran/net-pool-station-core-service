package net.pool.station.core.features.match.making.resource.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.match.making.resource.MatchMakingResource;
import net.pool.station.core.features.match.making.resource.repository.database.MatchMakingResourceRepository;
import net.pool.station.core.features.match.making.resource.repository.database.dao.MatchMakingResourceDaoMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MatchMakingResourceQueryService {
    MatchMakingResourceRepository repository;

    MatchMakingResourceDaoMapper daoMapper;

    protected List<MatchMakingResource> findAllByMatchMakingId(Long matchMakingId) {
        return daoMapper.toDto(repository.findAllByMatchMakingId(matchMakingId));
    }
}
