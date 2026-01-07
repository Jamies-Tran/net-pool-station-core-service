package net.pool.station.core.features.match.invitation.repository.database;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.auditor.Auditor;
import net.pool.station.core.bootstrap.enums.EMatchInvitationStatus;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "match_invitations")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MatchInvitationEntity extends Auditor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long matchInvitationId;
    Long matchMakingId;
    Long accountId;
    String message;
    String statusCode;
    String statusName;
    Boolean deleted;

    @PrePersist
    private void prePersist() {
        if (MyObjectUtils.isEmpty(statusCode)) {
            statusCode = EMatchInvitationStatus.SENT.getCode();
            statusName = EMatchInvitationStatus.SENT.getName();
        }

        deleted = false;
    }
}
