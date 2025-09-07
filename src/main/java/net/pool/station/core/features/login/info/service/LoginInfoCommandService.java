package net.pool.station.core.features.login.info.service;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyAuthenticationException;
import net.pool.station.core.bootstrap.utils.MyPasswordEncoderUtils;
import net.pool.station.core.bootstrap.utils.MySpringContext;
import net.pool.station.core.bootstrap.utils.MyTokenUtils;
import net.pool.station.core.domain.DomainCode;
import net.pool.station.core.domain.account.AccountUseCase;
import net.pool.station.core.domain.login.info.LoginInfo;
import net.pool.station.core.features.login.info.repository.database.LoginInfoEntity;
import net.pool.station.core.features.login.info.repository.database.LoginInfoEntityMapper;
import net.pool.station.core.features.login.info.repository.database.LoginInfoRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LoginInfoCommandService {
    LoginInfoRepository repository;

    LoginInfoEntityMapper mapper;

    MyTokenUtils tokenUtils;

    protected LoginInfo saveOrUpdate(
            @NonNull String email,
            @NonNull String password,
            Double latitude,
            Double longitude
    ) {
        PasswordEncoder passwordEncoder = MyPasswordEncoderUtils.passwordEncoder();
        passwordEncoder.encode(password);
        return accountUseCase().findByEmail(DomainCode.of(email))
                .map(account -> {
                    if (!passwordEncoder.matches(password, account.password())) {
                        throw new MyAuthenticationException();
                    }
                    Optional<LoginInfoEntity> exist = repository.findByEmail(email);
                    if (exist.isPresent()) {
                        LoginInfoEntity existGet = exist.get();
                        String accessToken = tokenUtils.generateAccessToken(account.email());
                        String refreshToken = UUID.randomUUID().toString();
                        LocalDateTime refreshExpiredAt = tokenUtils.convertRefreshExpiredAt();
                        LocalDateTime accessExpiredAt = tokenUtils.convertAccessExpiredAt();

                        existGet.setRefreshToken(refreshToken);
                        existGet.setRefreshExpiredAt(refreshExpiredAt);
                        existGet.setLatitude(latitude);
                        existGet.setLongitude(longitude);
                        LoginInfoEntity updateLoginInfo = repository.save(existGet);

                        return LoginInfo.builder()
                                .accessToken(accessToken)
                                .refreshToken(updateLoginInfo.getRefreshToken())
                                .accessExpiredAt(accessExpiredAt)
                                .refreshExpiredAt(updateLoginInfo.getRefreshExpiredAt())
                                .accountId(account.accountId())
                                .latitude(updateLoginInfo.getLatitude())
                                .longitude(updateLoginInfo.getLongitude())
                                .build();
                    } else {
                        String accessToken = tokenUtils.generateAccessToken(account.email());
                        String refreshToken = UUID.randomUUID().toString();
                        LocalDateTime refreshExpiredAt = tokenUtils.convertRefreshExpiredAt();
                        LocalDateTime accessExpiredAt = tokenUtils.convertAccessExpiredAt();
                        LoginInfo loginInfo = LoginInfo.builder()
                                .email(account.email())
                                .accessToken(accessToken)
                                .refreshToken(refreshToken)
                                .accessExpiredAt(accessExpiredAt)
                                .refreshExpiredAt(refreshExpiredAt)
                                .accountId(account.accountId())
                                .latitude(latitude)
                                .longitude(longitude)
                                .build();
                        repository.save(mapper.toEntity(loginInfo));

                        return loginInfo;
                    }
                })
                .orElseThrow(MyAuthenticationException::new);
    }

    protected void update(@NonNull String email, @NonNull Double latitude, @NonNull Double longitude) {
        repository.findByEmail(email)
                .ifPresent(
                        foundLoginInfo -> {
                            foundLoginInfo.setLatitude(latitude);
                            foundLoginInfo.setLongitude(longitude);
                            repository.save(foundLoginInfo);
                        }
                );
    }

    protected LoginInfo update(@NonNull String refreshToken) {
        return repository.findByRefreshToken(refreshToken)
                .map(foundLoginInfo -> {
                    String newAccessToken = tokenUtils.generateAccessToken(foundLoginInfo.getEmail());
                    LocalDateTime newAccessExpired = tokenUtils.convertAccessExpiredAt();
                    return LoginInfo.builder()
                            .accountId(foundLoginInfo.getAccountId())
                            .email(foundLoginInfo.getEmail())
                            .accessToken(newAccessToken)
                            .accessExpiredAt(newAccessExpired)
                            .refreshToken(foundLoginInfo.getRefreshToken())
                            .refreshExpiredAt(foundLoginInfo.getRefreshExpiredAt())
                            .latitude(foundLoginInfo.getLatitude())
                            .longitude(foundLoginInfo.getLongitude())
                            .build();
                })
                .orElseThrow(() -> new MyAuthenticationException("Mời bạn đăng nhập lại"));
    }

    protected void delete(@NonNull String email) {
        repository.findByEmail(email)
                .ifPresent(repository::delete);
    }

    private AccountUseCase accountUseCase() {
        return MySpringContext.getBean(AccountUseCase.class);
    }
}
