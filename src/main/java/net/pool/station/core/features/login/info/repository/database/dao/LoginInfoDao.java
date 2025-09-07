package net.pool.station.core.features.login.info.repository.database.dao;

public interface LoginInfoDao {
    String getAccountId();

    String getEmail();

    String getUsername();

    String getRoleCode();
}
