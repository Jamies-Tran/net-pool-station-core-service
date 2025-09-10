package net.pool.station.core.features.account.self.service;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceDuplicateException;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotFoundException;
import net.pool.station.core.bootstrap.enums.EAccountStatus;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import net.pool.station.core.domain.account.Account;
import net.pool.station.core.features.account.self.repository.database.AccountEntity;
import net.pool.station.core.features.account.self.repository.database.AccountEntityMapper;
import net.pool.station.core.features.account.self.repository.database.AccountRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountCommandService {
    AccountRepository repository;

    AccountEntityMapper mapper;

    protected Long save(@NonNull Account account) {
        validate(account);
        return repository.save(mapper.toEntity(account))
                .getAccountId();
    }

    protected void update(@NonNull Long accountId, @NonNull Account account) {
        repository.findByAccountId(accountId)
                .ifPresentOrElse(
                        foundAccount -> {
                            validate(account, foundAccount);
                            mapper.update(foundAccount, account);
                            repository.save(foundAccount);
                        },
                        () -> {
                            throw new MyResourceNotFoundException();
                        }
                );
    }

    protected void delete(@NonNull Long accountId) {
        repository.findByAccountId(accountId)
                .ifPresentOrElse(
                        foundAccount -> {
                            foundAccount.setDeleted(true);
                            repository.save(foundAccount);
                        },
                        () -> {
                            throw new MyResourceNotFoundException();
                        }
                );
    }

    private void validate(Account account) {
        repository.findByEmail(account.email())
                .ifPresent(_ -> {
                    throw new MyResourceDuplicateException("Email đã tồn tại");
                });

        repository.findByPhone(account.phone())
                .ifPresent(_ -> {
                    throw new MyResourceDuplicateException("Số điện thoại đã tồn tại");
                });

        repository.findByIdentification(account.identification())
                .ifPresent(_ -> {
                    throw new MyResourceDuplicateException("CCCCD đã tồn tại");
                });

        repository.findByUsername(account.username())
                .ifPresent(_ -> {
                    throw new MyResourceDuplicateException("Username đã tồn tại");
                });

    }

    protected void update(Long accountId, EAccountStatus status) {
        repository.findByAccountId(accountId)
                .ifPresentOrElse(
                        foundAccount -> {
                            foundAccount.setStatusCode(status.getCode());
                            foundAccount.setStatusName(status.getName());
                            repository.save(foundAccount);
                        },
                        () -> {
                            throw new MyResourceNotFoundException();
                        }
                );
    }

    private void validate(Account account, AccountEntity foundAccount) {
        if (MyObjectUtils.isNotEquals(account.email(), foundAccount.getEmail())) {
            repository.findByEmail(account.email())
                    .ifPresent(_ -> {
                        throw new MyResourceDuplicateException("Email đã tồn tại");
                    });
        }

        if (MyObjectUtils.isNotEquals(account.phone(), foundAccount.getPhone())) {
            repository.findByPhone(account.phone())
                    .ifPresent(_ -> {
                        throw new MyResourceDuplicateException("Số điện thoại đã tồn tại");
                    });
        }

        if (MyObjectUtils.isNotEquals(account.identification(), foundAccount.getIdentification())) {
            repository.findByIdentification(account.identification())
                    .ifPresent(_ -> {
                        throw new MyResourceDuplicateException("CCCCD đã tồn tại");
                    });
        }

        if (MyObjectUtils.isNotEquals(account.username(), foundAccount.getUsername())) {
            repository.findByUsername(account.username())
                    .ifPresent(_ -> {
                        throw new MyResourceDuplicateException("Username đã tồn tại");
                    });
        }
    }
}
