package net.pool.station.core.features.account.account.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotFoundException;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotValid;
import net.pool.station.core.bootstrap.enums.EAccountStatus;
import net.pool.station.core.bootstrap.utils.MyAuthorizationUtils;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.bootstrap.enums.ERole;
import net.pool.station.core.domain.account.Account;
import net.pool.station.core.domain.account.AccountCriteria;
import net.pool.station.core.domain.account.AccountUseCase;
import net.pool.station.core.domain.account.log.AccountLog;
import net.pool.station.core.domain.logging.factory.LoggingFactory;
import net.pool.station.core.domain.role.Role;
import net.pool.station.core.domain.role.RoleUseCase;
import net.pool.station.core.domain.station.account.StationAccount;
import net.pool.station.core.domain.station.account.StationAccountId;
import net.pool.station.core.domain.station.account.StationAccountUseCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountUseCaseService implements AccountUseCase {
    AccountCommandService commandService;

    AccountQueryService queryService;

    RoleUseCase roleUseCase;

    LoggingFactory<AccountLog> loggingService;

    StationAccountUseCase stationAccountUseCase;

    @Override
    @Transactional(readOnly = true)
    public Optional<Account> findByEmail(DomainKey<String> email) {
        return queryService.findByEmail(email.value())
                .map(foundAccount -> {
                    Role role = roleUseCase.findById(DomainKey.of(foundAccount.roleId()))
                            .orElse(Role.empty());
                    return foundAccount.withRole(role);
                });
    }

    @Override
    @Transactional
    public void save(Account account, ERole role) {
        Role foundRole = roleUseCase.findByCode(DomainKey.of(role.getCode()))
                .orElseThrow(MyResourceNotFoundException::new);
        Long savedId = commandService.save(account.withRoleId(foundRole.roleId()));
        loggingService.log(AccountLog.createSave(savedId));
    }

    @Override
    @Transactional
    public void save(Long stationId, Account account, ERole role) {
        Role foundRole = roleUseCase.findByCode(DomainKey.of(role.getCode()))
                .orElseThrow(MyResourceNotFoundException::new);
        Long savedId = commandService.save(account.withRoleId(foundRole.roleId()));
        loggingService.log(AccountLog.createSave(savedId));
        stationAccountUseCase.save(StationAccount.builder()
                .stationAccountId(StationAccountId.of(stationId, savedId))
                .build());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Account> findById(DomainKey<Long> accountId) {
        return queryService.findById(accountId.value())
                .map(foundAccount -> {
                    Role role = roleUseCase.findById(DomainKey.of(foundAccount.roleId()))
                            .orElse(Role.empty());
                    return foundAccount.withRole(role);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public List<Account> findAllByIdIn(List<Long> accountIds) {
        return queryService.findAllByAccountIdIn(accountIds);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Account> findAll(AccountCriteria criteria, PageRequest pageRequest) {
        return queryService.findAll(criteria, pageRequest);
    }

    @Override
    @Transactional
    public void update(DomainKey<Long> accountId, Account account) {
        commandService.update(accountId.value(), account);
        loggingService.log(AccountLog.createUpdate(accountId.value()));
    }

    @Override
    @Transactional
    public void enable(DomainKey<Long> accountId) {
        validateUpdateStatus(accountId);
        commandService.update(accountId.value(), EAccountStatus.ENABLE);
        loggingService.log(AccountLog.createEnable(accountId.value()));
    }

    @Override
    @Transactional
    public void disable(DomainKey<Long> accountId) {
        validateUpdateStatus(accountId);
        commandService.update(accountId.value(), EAccountStatus.DISABLE);
        loggingService.log(AccountLog.createDisable(accountId.value()));
    }

    @Override
    @Transactional
    public void activate(DomainKey<Long> accountId) {
        commandService.update(accountId.value(), EAccountStatus.ENABLE);
        loggingService.log(AccountLog.createVerify(accountId.value()));
    }

    @Override
    @Transactional
    public void delete(DomainKey<Long> accountId) {
        commandService.delete(accountId.value());
    }

    private void validateUpdateStatus(DomainKey<Long> accountId) {
        List<String> allowList = MyAuthorizationUtils.authorizeList();
        Account account = queryService.findById(accountId.value())
                .orElseThrow(MyResourceNotFoundException::new);
        Role role = roleUseCase.findById(DomainKey.of(account.roleId()))
                .orElseThrow(MyResourceNotFoundException::new);
        if (!allowList.contains(role.roleCode())) {
            throw new MyResourceNotValid("Không thể thực hiện thao tác lúc này!");
        }
    }
}
