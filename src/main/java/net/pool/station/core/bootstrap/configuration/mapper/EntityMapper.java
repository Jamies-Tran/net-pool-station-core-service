package net.pool.station.core.bootstrap.configuration.mapper;

import java.util.List;

public interface EntityMapper<E, D> {
    E toEntity(D d);

    D toDto(E e);

    List<E> toEntity(List<D> ds);

    List<D> toDto(List<E> es);
}
