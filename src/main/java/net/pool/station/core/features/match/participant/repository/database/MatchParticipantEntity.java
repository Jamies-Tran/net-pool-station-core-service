package net.pool.station.core.features.match.participant.repository.database;


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
@Table(name = "match_participants")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MatchParticipantEntity extends Auditor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long matchParticipantId;
    Long matchMakingId;
    Long accountId;
    String typeCode;
    String typeName;
    String paymentMethodCode;
    String paymentMethodName;
    String readyStatusCode;
    String readyStatusName;
    Integer shareAmount;
    Integer paidDeposit;
    String statusCode;
    String statusName;
}
