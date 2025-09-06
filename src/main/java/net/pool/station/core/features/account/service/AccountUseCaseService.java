package net.pool.station.core.features.account.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotFoundException;
import net.pool.station.core.domain.DomainCode;
import net.pool.station.core.bootstrap.enums.ERole;
import net.pool.station.core.domain.account.Account;
import net.pool.station.core.domain.account.AccountCriteria;
import net.pool.station.core.domain.account.AccountUseCase;
import net.pool.station.core.domain.role.Role;
import net.pool.station.core.domain.role.RoleUseCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountUseCaseService implements AccountUseCase {
    AccountCommandService commandService;

    AccountQueryService queryService;

    RoleUseCase roleUseCase;

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
        commandService.save(account.withRoleId(foundRole.roleId()));
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
    public Page<Account> findAll(AccountCriteria criteria, Pageable pageable) {
        return queryService.findAll(criteria, pageable);
    }

    @Override
    @Transactional
    public void update(DomainCode<Long> accountId, Account account) {
        commandService.update(accountId.value(), account);
    }

    @Override
    @Transactional
    public void delete(DomainCode<Long> accountId) {
        commandService.delete(accountId.value());
    }
}
