package net.pool.station.core.bootstrap.configuration.mapper;

import java.util.List;

public interface ModelMapper<M, D> {
    M toModel(D d);

    D toDto(M m);

    List<M> toModel(List<D> ds);

    List<D> toDto(List<M> ms);
}
