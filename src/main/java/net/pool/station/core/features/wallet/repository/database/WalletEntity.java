package net.pool.station.core.features.wallet.repository.database;

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
import net.pool.station.core.bootstrap.enums.EWalletStatus;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "wallets")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WalletEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long walletId;

    Long accountId;

    Double balance;

    Boolean directPayment;

    String statusCode;

    String statusName;

    Boolean deleted;

    @PrePersist
    private void prePersist() {
        if (MyObjectUtils.isEmpty(statusCode)) {
            statusCode = EWalletStatus.DISABLE.getCode();
            statusName = EWalletStatus.DISABLE.getName();
        }

        if (MyObjectUtils.isEmpty(balance)) {
            balance = 0.0;
        }

        deleted = false;
    }

    @PreUpdate
    private void preUpdate() {
        if (balance < 0.0) {
            directPayment = false;
        }
    }
}
