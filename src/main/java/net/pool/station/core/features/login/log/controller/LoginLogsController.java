package net.pool.station.core.features.login.log.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.rest.response.MyPageResponse;
import net.pool.station.core.bootstrap.rest.response.MySorter;
import net.pool.station.core.domain.logging.factory.LoggingFactory;
import net.pool.station.core.domain.login.log.LoginLog;
import net.pool.station.core.domain.login.log.LoginLogCriteria;
import net.pool.station.core.domain.login.log.LoginLogUseCase;
import net.pool.station.core.features.login.log.controller.models.LoginLogResponse;
import net.pool.station.core.features.login.log.controller.models.LoginLogResponseMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LoginLogsController implements LoginLogsApi {
    LoggingFactory<LoginLog> loggingService;

    LoginLogResponseMapper responseMapper;

    @Override
    public MyPageResponse<LoginLogResponse> findAll(
            Long accountId,
            String search,
            List<LocalDateTime> timeRange,
            List<String> logTypeCodes,
            String sorter, Integer current, Integer pageSize
    ) {
        LoginLogCriteria criteria = LoginLogCriteria.of(accountId, search, timeRange, logTypeCodes);
        PageRequest pageRequest = PageRequest.of(current, pageSize, MySorter.of(sorter));
        Page<LoginLogResponse> responses = loggingService.findAll(criteria, pageRequest)
                .map(responseMapper::toModel);

        return MyPageResponse.success(responses);
    }
}
