package net.pool.station.core.features.account.self.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotFoundException;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotValid;
import net.pool.station.core.bootstrap.enums.EAccountStatus;
import net.pool.station.core.bootstrap.utils.MyAuthorizationUtils;
import net.pool.station.core.domain.DomainCode;
import net.pool.station.core.bootstrap.enums.ERole;
import net.pool.station.core.domain.account.Account;
import net.pool.station.core.domain.account.AccountCriteria;
import net.pool.station.core.domain.account.AccountUseCase;
import net.pool.station.core.domain.account.log.AccountLog;
import net.pool.station.core.domain.logging.factory.LoggingFactory;
import net.pool.station.core.domain.role.Role;
import net.pool.station.core.domain.role.RoleUseCase;
import org.springframework.data.domain.Page;
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

    @Override
    @Transactional(readOnly = true)
    public Optional<Account> findByEmail(DomainCode<String> email) {
        return queryService.findByEmail(email.value())
                .map(foundAccount -> {
                    Role role = roleUseCase.findById(DomainCode.of(foundAccount.roleId()))
                            .orElse(Role.empty());
                    return foundAccount.withRole(role);
                });
    }

    @Override
    @Transactional
    public void save(Account account, ERole role) {
        Role foundRole = roleUseCase.findByCode(DomainCode.of(role.getCode()))
                .orElseThrow(MyResourceNotFoundException::new);
        Long savedId = commandService.save(account.withRoleId(foundRole.roleId()));
        loggingService.log(AccountLog.createSave(savedId));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Account> findById(DomainCode<Long> accountId) {
        return queryService.findById(accountId.value())
                .map(foundAccount -> {
                    Role role = roleUseCase.findById(DomainCode.of(foundAccount.roleId()))
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
    public Page<Account> findAll(AccountCriteria criteria, Pageable pageable) {
        return queryService.findAll(criteria, pageable);
    }

    @Override
    @Transactional
    public void update(DomainCode<Long> accountId, Account account) {
        commandService.update(accountId.value(), account);
        loggingService.log(AccountLog.createUpdate(accountId.value()));
    }

    @Override
    @Transactional
    public void enable(DomainCode<Long> accountId) {
        validateUpdateStatus(accountId);
        commandService.update(accountId.value(), EAccountStatus.ENABLE);
        loggingService.log(AccountLog.createEnable(accountId.value()));
    }

    @Override
    @Transactional
    public void disable(DomainCode<Long> accountId) {
        validateUpdateStatus(accountId);
        commandService.update(accountId.value(), EAccountStatus.DISABLE);
        loggingService.log(AccountLog.createDisable(accountId.value()));
    }

    @Override
    @Transactional
    public void activate(DomainCode<Long> accountId) {
        commandService.update(accountId.value(), EAccountStatus.ENABLE);
        loggingService.log(AccountLog.createVerify(accountId.value()));
    }

    @Override
    @Transactional
    public void delete(DomainCode<Long> accountId) {
        commandService.delete(accountId.value());
    }

    private void validateUpdateStatus(DomainCode<Long> accountId) {
        List<String> allowList = MyAuthorizationUtils.authorizeList();
        Account account = queryService.findById(accountId.value())
                .orElseThrow(MyResourceNotFoundException::new);
        Role role = roleUseCase.findById(DomainCode.of(account.roleId()))
                .orElseThrow(MyResourceNotFoundException::new);
        if (!allowList.contains(role.roleCode())) {
            throw new MyResourceNotValid("Không thể thực hiện thao tác lúc này!");
        }
    }
}
