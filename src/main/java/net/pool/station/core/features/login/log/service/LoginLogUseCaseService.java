package net.pool.station.core.features.login.log.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import net.pool.station.core.bootstrap.utils.MySpringContext;
import net.pool.station.core.domain.account.Account;
import net.pool.station.core.domain.account.AccountUseCase;
import net.pool.station.core.domain.login.log.LoginLog;
import net.pool.station.core.domain.login.log.LoginLogCriteria;
import net.pool.station.core.domain.login.log.LoginLogUseCase;
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
public class LoginLogUseCaseService implements LoginLogUseCase {
    LoginLogCommandService commandService;

    LoginLogQueryService queryService;

    @Override
    @Transactional
    public void save(LoginLog loginLog) {
        commandService.save(loginLog);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LoginLog> findAll(LoginLogCriteria criteria, PageRequest pageRequest) {
        Page<LoginLog> loginLogs = queryService.findAll(criteria, pageRequest);
        List<Long> createdByList = loginLogs.stream()
                .map(loginLog -> MyObjectUtils.convertToLong(loginLog.createdBy()))
                .toList();
        Map<String, String> createdByUsernameMap = createdByUsernameMap(createdByList);

        return loginLogs
                .map(loginLog -> loginLog
                        .withCreatedByUsername(createdByUsernameMap
                                .computeIfAbsent(loginLog.createdBy(), id -> "-")));
    }

    protected Map<String, String> createdByUsernameMap(List<Long> createdByList) {
        AccountUseCase accountUseCase = MySpringContext.getBean(AccountUseCase.class);

        return accountUseCase.findAllByIdIn(createdByList)
                .stream()
                .collect(Collectors
                        .toMap(a -> MyObjectUtils
                                .convertToString(a.accountId()), Account::username));
    }
}
