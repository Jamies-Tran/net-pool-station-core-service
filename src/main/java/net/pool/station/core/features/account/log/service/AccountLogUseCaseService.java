package net.pool.station.core.features.account.log.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import net.pool.station.core.bootstrap.utils.MySpringContext;
import net.pool.station.core.domain.account.Account;
import net.pool.station.core.domain.account.AccountUseCase;
import net.pool.station.core.domain.account.log.AccountLog;
import net.pool.station.core.domain.account.log.AccountLogCriteria;
import net.pool.station.core.domain.account.log.AccountLogUseCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountLogUseCaseService implements AccountLogUseCase {
    AccountLogCommandService commandService;

    AccountLogQueryService queryService;

    @Override
    @Transactional
    public void save(AccountLog accountLog) {
        commandService.save(accountLog);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AccountLog> findAll(AccountLogCriteria criteria, Pageable pageable) {
        Page<AccountLog> accountLogs = queryService.findAll(criteria, pageable);
        List<Long> createdByList = accountLogs.stream()
                .map(accountLog -> MyObjectUtils.convertToLong(accountLog.createdBy()))
                .toList();
        Map<Long, String> createdByUsernameMap = createdByUsernameMap(createdByList);

        return accountLogs.map(accountLog -> accountLog
                .withCreatedByUsername(createdByUsernameMap
                        .computeIfAbsent(MyObjectUtils.convertToLong(accountLog.createdBy()), id -> "-")));
    }

    private Map<Long, String> createdByUsernameMap(List<Long> createdByList) {
        AccountUseCase accountUseCase = MySpringContext.getBean(AccountUseCase.class);

        return accountUseCase.findAllByIdIn(createdByList)
                .stream()
                .collect(Collectors.toMap(Account::accountId, Account::username));
    }
}
