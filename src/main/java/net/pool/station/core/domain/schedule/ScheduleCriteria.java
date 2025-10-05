package net.pool.station.core.domain.schedule;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Builder;
import net.pool.station.core.bootstrap.enums.ERole;
import net.pool.station.core.bootstrap.enums.EScheduleStatus;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import net.pool.station.core.bootstrap.utils.MyRequestContext;
import net.pool.station.core.domain.login.info.LoginInfo;
import net.pool.station.core.features.schedule.repository.database.ScheduleEntity;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
public record ScheduleCriteria(
        Long stationId,
        List<LocalDate> dateRange,
        List<String> statusCodes
) {
    public ScheduleCriteria {
        dateRange = MyObjectUtils.defaultValue(dateRange, new TypeReference<>() {});
        statusCodes = MyObjectUtils.defaultValue(statusCodes, new TypeReference<>() {});
    }

    public static ScheduleCriteria of(
            Long stationId,
            List<LocalDate> dateRange,
            List<String> statusCodes
    ) {
        return ScheduleCriteria
                .builder()
                .stationId(stationId)
                .dateRange(dateRange)
                .statusCodes(statusCodes)
                .build();
    }

    public Specification<ScheduleEntity> specifications() {
        List<Specification<ScheduleEntity>> specifications = new ArrayList<>();

        specifications.add((root, query, cb) ->
                cb.equal(root.get("stationId"), stationId));

        specifications.add((root, query, cb) ->
                cb.equal(root.get("deleted"), false));

        if (MyObjectUtils.isNotEmpty(dateRange) && MyObjectUtils.isEquals(dateRange.size(), 2)) {
            specifications.add((root, query, cb) ->
                    cb.between(root.get("date"), dateRange.get(0), dateRange.get(1)));
        }

        if (MyObjectUtils.isNotEmpty(statusCodes)) {
            LoginInfo loginInfo = MyRequestContext.currentLoginInfo()
                            .orElse(LoginInfo.currentLoginInfoEmpty());
            if (loginInfo.isLoginEmpty() || MyObjectUtils.isEquals(loginInfo.roleCode(), ERole.PLAYER.getCode())) {
                specifications.add((root, query, cb) ->
                        cb.equal(root.get("statusCode"), EScheduleStatus.ENABLED.getCode()));
            }

            specifications.add((root, query, cb) ->
                    cb.in(root.get("statusCode")).in(statusCodes));
        }

        return specifications.stream().reduce(Specification.where(null), Specification::and);
    }
}
