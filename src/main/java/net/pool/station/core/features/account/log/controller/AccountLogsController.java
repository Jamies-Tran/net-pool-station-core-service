package net.pool.station.core.features.account.log.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.rest.response.MyPageResponse;
import net.pool.station.core.bootstrap.rest.response.MySorter;
import net.pool.station.core.domain.account.log.AccountLogCriteria;
import net.pool.station.core.domain.account.log.AccountLogUseCase;
import net.pool.station.core.features.account.log.controller.models.AccountLogResponse;
import net.pool.station.core.features.account.log.controller.models.AccountLogResponseMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountLogsController implements AccountLogsApi {
    AccountLogUseCase useCase;

    AccountLogResponseMapper responseMapper;

    @Override
    public MyPageResponse<AccountLogResponse> findAll(
            Long accountId,
            String search,
            List<LocalDateTime> timeRange,
            List<String> actionCodes,
            String sorter, Integer current, Integer pageSize
    ) {
        AccountLogCriteria criteria = AccountLogCriteria.of(search, accountId, timeRange, actionCodes);
        PageRequest pageRequest = PageRequest.of(current, pageSize, MySorter.of(sorter));
        Page<AccountLogResponse> responses = useCase.findAll(criteria, pageRequest)
                .map(responseMapper::toModel);

        return MyPageResponse.success(responses);
    }
}
