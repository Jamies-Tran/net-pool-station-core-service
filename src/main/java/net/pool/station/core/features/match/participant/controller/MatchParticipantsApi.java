package net.pool.station.core.features.match.participant.controller;

import net.pool.station.core.bootstrap.rest.response.MyPageResponse;
import net.pool.station.core.features.match.making.match.making.controller.models.participant.MatchParticipantResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/v1/api/match-participant")
public interface MatchParticipantsApi {
    @GetMapping
    MyPageResponse<MatchParticipantResponse> findAll(
            @RequestParam(required = false, value = "search", defaultValue = "")
            String search,

            @RequestParam(value = "matchMakingId", defaultValue = "")
            Long matchMakingId,

            @RequestParam(required = false, value = "typeCodes", defaultValue = "")
            List<String> typeCodes,

            @RequestParam(required = false, value = "statusCodes", defaultValue = "")
            List<String> statusCodes,

            @RequestParam(required = false, value = "sorter", defaultValue = "matchParticipantId")
            String sorter,

            @RequestParam(required = false, value = "current", defaultValue = "0")
            Integer current,

            @RequestParam(required = false, value = "pageSize", defaultValue = "25")
            Integer pageSize
    );
}
