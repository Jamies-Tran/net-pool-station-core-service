package net.pool.station.core.features.match.making.match.making.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.match.making.MatchMaking;
import net.pool.station.core.domain.match.making.MatchMakingCriteria;
import net.pool.station.core.features.match.making.match.making.repository.database.MatchMakingMapper;
import net.pool.station.core.features.match.making.match.making.repository.database.MatchMakingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MatchMakingQueryService {
    MatchMakingRepository repository;

    MatchMakingMapper mapper;

    protected Optional<MatchMaking> findById(Long matchMakingId) {
        return repository.findByMatchMakingIdAndDeletedFalse(matchMakingId)
                .map(mapper::toDto);
    }

    protected Page<MatchMaking> findAll(MatchMakingCriteria criteria, PageRequest pageRequest) {
        return repository.findAll(criteria, pageRequest)
                .map(mapper::toDto);
    }

    protected Optional<Long> findOwnerWalletIdByStationId(Long stationId) {
        return repository.findOwnerWalletIdByStationId(stationId);
    }
}
