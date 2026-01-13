package net.pool.station.core.features.match.joining.controller;

import jakarta.validation.Valid;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.features.match.joining.controller.models.MatchJoiningRegistrationRequest;
import net.pool.station.core.features.match.joining.controller.models.MatchJoiningRegistrationResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/v1/api/match-joining-registrations/{matchJoiningRegistrationId}")
@PreAuthorize( "hasRole('ROLE_PLAYER')")
public interface MatchJoiningRegistrationApi {
    @GetMapping
    MyValueResponse<MatchJoiningRegistrationResponse> findById(@PathVariable Long matchJoiningRegistrationId);

    @PutMapping
    MyValueResponse<?> update(@PathVariable Long matchJoiningRegistrationId,
                              @RequestBody @Valid MatchJoiningRegistrationRequest request);

    @PatchMapping("/accept")
    MyValueResponse<?> accept(@PathVariable Long matchJoiningRegistrationId);

    @PatchMapping("/deny")
    MyValueResponse<?> deny(@PathVariable Long matchJoiningRegistrationId);

    @PatchMapping("/cancel")
    MyValueResponse<?> cancel(@PathVariable Long matchJoiningRegistrationId);
}
