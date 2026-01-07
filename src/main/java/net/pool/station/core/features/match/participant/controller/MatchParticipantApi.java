package net.pool.station.core.features.match.participant.controller;

import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/api/match-participant/{matchParticipantId}")
public interface MatchParticipantApi {
    @PatchMapping("/empty")
    MyValueResponse<?> empty(@PathVariable Long matchParticipantId);
}
