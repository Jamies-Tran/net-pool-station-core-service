package net.pool.station.core.bootstrap.configuration.security;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.DomainCode;
import net.pool.station.core.bootstrap.utils.MyPasswordEncoderUtils;
import net.pool.station.core.domain.account.Account;
import net.pool.station.core.domain.account.AccountUseCase;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MyUserDetailsService implements UserDetailsService {
    AccountUseCase accountUseCase;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> account = accountUseCase.findByEmail(DomainCode.of(username));

        return account
                .map(foundAccount -> User.builder()
                        .username(foundAccount.accountId().toString())
                        .password(MyPasswordEncoderUtils.passwordEncoder().encode(foundAccount.password()))
                        .disabled(false)
                        .authorities(new SimpleGrantedAuthority("ROLE_%s"
                                .formatted(foundAccount.role().roleCode())))
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
