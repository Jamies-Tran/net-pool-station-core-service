package net.pool.station.core.features.wallet.ledger.repository.database;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.auditor.Auditor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "wallet_ledgers")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WalletLedgerEntity extends Auditor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long walletLedgerId;
    Long walletId;
    Long transactionId;
    Integer currentBalance;
    Integer changeAmount;
    Integer newBalance;
    Integer chargedCommission;
    Integer newContributedCommission;
}
