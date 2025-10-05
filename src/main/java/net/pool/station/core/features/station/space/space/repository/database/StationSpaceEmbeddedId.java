package net.pool.station.core.features.station.space.space.repository.database;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class StationSpaceEmbeddedId implements Serializable {
    Long stationId;

    Long spaceId;

    @Override
    public int hashCode() {
        return Objects.hash(stationId, spaceId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof StationSpaceEmbeddedId)) return false;
        StationSpaceEmbeddedId that = (StationSpaceEmbeddedId) obj;
        return Objects.equals(stationId, that.stationId) &&
                Objects.equals(spaceId, that.spaceId);
    }
}
