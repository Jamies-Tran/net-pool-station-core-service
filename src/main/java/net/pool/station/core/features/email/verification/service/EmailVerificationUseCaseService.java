package net.pool.station.core.features.email.verification.service;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceDuplicateException;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotFoundException;
import net.pool.station.core.bootstrap.enums.EMailType;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.account.AccountUseCase;
import net.pool.station.core.domain.email.sending.EmailSending;
import net.pool.station.core.domain.email.sending.EmailUseCase;
import net.pool.station.core.domain.email.verification.EmailVerification;
import net.pool.station.core.domain.email.verification.EmailVerificationUseCase;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailVerificationUseCaseService implements EmailVerificationUseCase {
    EmailVerificationCommandService commandService;

    EmailVerificationQueryService queryService;

    AccountUseCase accountUseCase;

    EmailUseCase emailUseCase;

    @Override
    @Transactional
    @Retryable(retryFor = {MyResourceDuplicateException.class})
    public void save(@NonNull EmailVerification emailVerification) {
        EmailVerification savedEmailVerification = commandService
                .save(emailVerification);
        emailUseCase.sendMail(EmailSending.composeMessage(
                EMailType.VALIDATE_ACCOUNT,
                savedEmailVerification.verificationCode(),
                savedEmailVerification.email()
        ));
    }

    @Override
    @Transactional
    public void verify(@NonNull DomainKey<String> verificationCode) {
        EmailVerification emailVerification = queryService
                .findByVerificationCode(verificationCode.value())
                .orElseThrow(MyResourceNotFoundException::new);
        accountUseCase.findByEmail(DomainKey.of(emailVerification.email()))
                        .ifPresent(account -> {
                            accountUseCase.activate(DomainKey.of(account.accountId()));
                        });
        commandService.delete(emailVerification.emailVerificationId());
    }
}
