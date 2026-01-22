package net.pool.station.core.features.match.making.slot.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.match.making.slot.MatchMakingSlot;
import net.pool.station.core.features.match.making.slot.respository.database.MatchMakingSlotMapper;
import net.pool.station.core.features.match.making.slot.respository.database.MatchMakingSlotRepository;
import net.pool.station.core.features.match.making.slot.respository.database.dao.MatchMakingSlotDaoMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MatchMakingSlotQueryService {
    MatchMakingSlotRepository repository;

    MatchMakingSlotDaoMapper daoMapper;

    protected List<MatchMakingSlot> findAllByMatchMakingId(Long matchMakingId) {
        return daoMapper.toDto(repository.findAllByMatchMakingId(matchMakingId));
    }

    protected List<MatchMakingSlot> findAllByStationResourceIdAndStatusCodeIn(
            Long stationResourceId, List<String> matchMakingStatusCodes) {
        return daoMapper.toDto(repository.findAllByStationResourceIdAndStatusCodeIn(
                stationResourceId, matchMakingStatusCodes));
    }
}
