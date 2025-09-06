package net.pool.station.core.bootstrap.configuration.auditor;

import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class Auditor {
    String createdBy;

    String updatedBy;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;

    public abstract void prePersist();

    public abstract void preUpdate();
}
