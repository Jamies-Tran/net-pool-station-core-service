package net.pool.station.core.features.station.resource.specs.repository.database.dao;

public interface StationResourceSpecDao {
    String getPcCpu();

    String getPcRam();

    String getPcGpu();

    String getPcMonitor();

    String getPcKeyboard();

    String getPcMouse();

    String getPcHeadphone();

    String getBtTableDetail();

    String getBtCueDetail();

    String getBtBallDetail();

    String getCsConsoleModel();

    String getCsTvModel();

    String getCsControllerType();

    Integer getCsControllerCount();

    String getTypeCode();

    String getTypeName();
}
