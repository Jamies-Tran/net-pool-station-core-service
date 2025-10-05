package net.pool.station.core.features.station.account.repository.database;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StationAccountEmbeddedId implements Serializable {
    Long stationId;
    Long accountId;

    @Override
    public int hashCode() {
        return Objects.hash(stationId, accountId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof StationAccountEmbeddedId)) return false;
        StationAccountEmbeddedId that = (StationAccountEmbeddedId) obj;
        return Objects.equals(stationId, that.stationId) &&
                Objects.equals(accountId, that.accountId);
    }
}
