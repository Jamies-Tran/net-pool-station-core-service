package net.pool.station.core.features.match.making.match.making.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyAuthenticationException;
import net.pool.station.core.bootstrap.utils.MyRequestContext;
import net.pool.station.core.domain.login.info.LoginInfo;
import net.pool.station.core.domain.match.making.MatchMaking;
import net.pool.station.core.domain.match.making.MatchMakingCriteria;
import net.pool.station.core.features.match.making.match.making.repository.database.MatchMakingMapper;
import net.pool.station.core.features.match.making.match.making.repository.database.MatchMakingRepository;
import net.pool.station.core.features.match.making.match.making.repository.database.dao.MatchMakingDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MatchMakingQueryService {
    MatchMakingRepository repository;

    MatchMakingMapper mapper;

    protected Optional<MatchMaking> findById(Long matchMakingId) {
        LoginInfo loginInfo = MyRequestContext.currentLoginInfo()
                .orElseThrow(MyAuthenticationException::new);
        return repository.findByMatchMakingIdAndDeletedFalse(matchMakingId, loginInfo.accountId())
                .map(m -> mapper.toDto(m.getMatchMaking()).withAllowJoin(m.getAllowJoin()));
    }

    protected Page<MatchMaking> findAll(MatchMakingCriteria criteria, PageRequest pageRequest) {
        LoginInfo loginInfo = MyRequestContext.currentLoginInfo()
                .orElseThrow(MyAuthenticationException::new);
        Page<MatchMakingDao> dao = repository.findAll(criteria, loginInfo.accountId(), pageRequest
                .withSort(Sort.unsorted()));
        Map<Long, Boolean> matchMakingIdMap = dao.stream().collect(Collectors
                .toMap(MatchMakingDao::getMatchMakingId, MatchMakingDao::getAllowJoin));

        return repository.findAllByMatchMakingIdIn(matchMakingIdMap.keySet().stream().toList(), pageRequest)
                .map(m -> mapper.toDto(m).withAllowJoin(matchMakingIdMap
                        .computeIfAbsent(m.getMatchMakingId(), k -> false)));
    }

    protected Optional<Long> findOwnerWalletIdByStationId(Long stationId) {
        return repository.findOwnerWalletIdByStationId(stationId);
    }
}
