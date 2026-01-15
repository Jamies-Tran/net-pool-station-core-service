package net.pool.station.core.features.station.resource.resource.repository.database.dao;

public interface StationResourceDao {
    Long getStationResourceId();

    String getRowCode();

    String getRowName();

    Long getAreaId();

    String getResourceCode();

    String getResourceName();

    String getTypeCode();

    String getTypeName();

    String getStatusCode();

    String getStatusName();

    Integer getPrice();

    Integer getDisplayOrder();

    Boolean getAllowDirectPayment();
}
