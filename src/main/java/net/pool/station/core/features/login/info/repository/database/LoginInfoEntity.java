package net.pool.station.core.features.login.info.repository.database;

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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "login_info")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginInfoEntity extends Auditor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long loginInfoId;

    Long accountId;

    String email;

    String refreshToken;

    LocalDateTime refreshExpiredAt;

    Double latitude;

    Double longitude;

    @Override
    @PrePersist
    public void prePersist() {
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
