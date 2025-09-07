package net.pool.station.core.bootstrap.configuration.mapper;

public interface DaoMapper<Dao, D> {
    D toDto(Dao dao);
}
