package net.pool.station.core.features.account.repository.database;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.auditor.Auditor;
import net.pool.station.core.bootstrap.enums.EAccountStatus;
import net.pool.station.core.bootstrap.utils.MyPasswordEncoderUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "accounts")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountEntity extends Auditor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long accountId;

    Long roleId;

    String avatar;

    String username;

    String password;

    String identification;

    String phone;

    String email;

    String statusCode;

    String statusName;

    Boolean deleted;

    @Override
    @PrePersist
    public void prePersist() {
        statusCode = Optional.ofNullable(statusCode).orElse(EAccountStatus.ENABLE.getCode());
        statusName = Optional.ofNullable(statusName).orElse(EAccountStatus.ENABLE.getName());
        password = MyPasswordEncoderUtils.passwordEncoder()
                .encode(Optional.ofNullable(password).orElse(""));
        deleted = false;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (Objects.nonNull(auth) && auth.isAuthenticated()) {
            createdBy = (String) auth.getPrincipal();
            updatedBy = (String) auth.getPrincipal();
        } else {
            createdBy = "Anonymous";
            updatedBy = "Anonymous";
        }
    }

    @Override
    @PreUpdate
    public void preUpdate() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        updatedAt = LocalDateTime.now();
        if (Objects.nonNull(auth) && auth.isAuthenticated()) {
            updatedBy = (String) auth.getPrincipal();
        } else {
            updatedBy = "Anonymous";
        }
    }
}
