package net.pool.station.core.features.match.joining.controller;

import jakarta.validation.Valid;
import net.pool.station.core.bootstrap.rest.response.MyPageResponse;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.features.match.joining.controller.models.MatchJoiningRegistrationRequest;
import net.pool.station.core.features.match.joining.controller.models.MatchJoiningRegistrationResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/v1/api/match-joining-registrations")
@PreAuthorize( "hasRole('ROLE_PLAYER')")
public interface MatchJoiningRegistrationsApi {
    @PostMapping
    MyValueResponse<?> save(@RequestBody @Valid MatchJoiningRegistrationRequest request);

    @GetMapping
    MyPageResponse<MatchJoiningRegistrationResponse> findAll(
            @RequestParam(required = false, value = "matchMakingId", defaultValue = "")
            Long matchMakingId,

            @RequestParam(required = false, value = "createdBy", defaultValue = "")
            String createdBy,

            @RequestParam(required = false, value = "timeRange", defaultValue = "")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            List<LocalDateTime> timeRange,

            @RequestParam(required = false, value = "statusCodes", defaultValue = "")
            List<String> statusCodes,

            @RequestParam(required = false, value = "sorter", defaultValue = "")
            String sorter,

            @RequestParam(required = false, value = "current", defaultValue = "0")
            Integer current,

            @RequestParam(required = false, value = "pageSize", defaultValue = "10")
            Integer pageSize
    );
}
