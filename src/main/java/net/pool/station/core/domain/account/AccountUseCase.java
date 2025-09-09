package net.pool.station.core.domain.account;

import net.pool.station.core.bootstrap.enums.EAccountStatus;
import net.pool.station.core.domain.DomainCode;
import net.pool.station.core.bootstrap.enums.ERole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface AccountUseCase {
    Optional<Account> findByEmail(DomainCode<String> email);

    void save(Account account, ERole role);

    Page<Account> findAll(AccountCriteria criteria, Pageable pageable);

    Optional<Account> findById(DomainCode<Long> accountId);

    void update(DomainCode<Long> accountId, Account account);

    void enable(DomainCode<Long> accountId);

    void disable(DomainCode<Long> accountId);

    void activate(DomainCode<Long> accountId);

    void delete(DomainCode<Long> accountId);
}
