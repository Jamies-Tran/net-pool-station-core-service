package net.pool.station.core.features.station.log.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import net.pool.station.core.bootstrap.utils.MySpringContext;
import net.pool.station.core.domain.account.Account;
import net.pool.station.core.domain.account.AccountUseCase;
import net.pool.station.core.domain.station.log.StationLog;
import net.pool.station.core.domain.station.log.StationLogCriteria;
import net.pool.station.core.domain.station.log.StationLogUseCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StationLogUseCaseService implements StationLogUseCase {
    StationLogCommandService commandService;

    StationLogQueryService queryService;

    @Override
    @Transactional
    public void save(StationLog stationLog) {
        commandService.save(stationLog);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StationLog> findAll(StationLogCriteria criteria, PageRequest pageRequest) {
        Page<StationLog> stationLogs = queryService.findAll(criteria, pageRequest);
        List<Long> createdByList = stationLogs.stream()
                .map(s -> MyObjectUtils.convertToLong(s.createdBy()))
                .toList();
        Map<String, String> createdByUsernameMap = createdByUsernameMap(createdByList);

        return stationLogs.map(s -> s.withCreatedByUsername(createdByUsernameMap
                .computeIfAbsent(s.createdBy(), st -> "")));
    }

    private Map<String, String> createdByUsernameMap(List<Long> createdByList) {
        AccountUseCase accountUseCase = MySpringContext.getBean(AccountUseCase.class);
        return accountUseCase.findAllByIdIn(createdByList)
                .stream()
                .collect(Collectors
                        .toMap(a -> MyObjectUtils.convertToString(a.accountId()), Account::username));
    }
}
