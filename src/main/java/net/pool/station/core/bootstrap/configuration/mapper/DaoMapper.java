package net.pool.station.core.bootstrap.configuration.mapper;

import java.util.List;

public interface DaoMapper<Dao, D> {
    D toDto(Dao dao);

    List<D> toDto(List<Dao> daos);
}
