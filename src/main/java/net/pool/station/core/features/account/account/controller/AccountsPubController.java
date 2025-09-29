package net.pool.station.core.features.account.account.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.enums.ERole;
import net.pool.station.core.bootstrap.rest.response.MyPageResponse;
import net.pool.station.core.bootstrap.rest.response.MySorter;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.domain.account.AccountCriteria;
import net.pool.station.core.domain.account.AccountUseCase;
import net.pool.station.core.features.account.account.controller.models.AccountRequest;
import net.pool.station.core.features.account.account.controller.models.AccountRequestMapper;
import net.pool.station.core.features.account.account.controller.models.AccountResponse;
import net.pool.station.core.features.account.account.controller.models.AccountResponseMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountsPubController implements AccountsPubApi {
    AccountUseCase useCase;

    AccountRequestMapper requestMapper;

    AccountResponseMapper responseMapper;

    @Override
    public MyValueResponse<?> saveForPlayer(AccountRequest request) {
        useCase.save(requestMapper.toDto(request), ERole.PLAYER);

        return MyValueResponse.successNoData();
    }

    @Override
    public MyValueResponse<?> saveForStationOwner(AccountRequest request) {
        useCase.save(requestMapper.toDto(request), ERole.STATION_OWNER);

        return MyValueResponse.successNoData();
    }

    @Override
    public MyPageResponse<AccountResponse> findAll(
            String search,
            List<LocalDateTime> timeRange,
            List<String> statusCodes,
            List<Long> roleIds,
            String sorter,
            Integer current,
            Integer pageSize
    ) {
        AccountCriteria criteria = AccountCriteria.of(search, timeRange, statusCodes, roleIds);
        PageRequest pageRequest = PageRequest.of(current, pageSize, MySorter.of(sorter));
        Page<AccountResponse> responses = useCase.findAll(criteria, pageRequest)
                .map(responseMapper::toModel);

        return MyPageResponse.success(responses);
    }
}
