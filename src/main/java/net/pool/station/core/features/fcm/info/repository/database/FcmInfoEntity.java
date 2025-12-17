package net.pool.station.core.features.fcm.info.repository.database;

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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "fcm_info")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FcmInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long fcmInfoId;
    Long accountId;
    String deviceId;
    String deviceType;
    String fcmToken;
}
