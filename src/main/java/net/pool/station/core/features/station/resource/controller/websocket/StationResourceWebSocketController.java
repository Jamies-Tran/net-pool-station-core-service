package net.pool.station.core.features.station.resource.controller.websocket;

import net.pool.station.core.domain.station.resource.StationResourceUseCase;
import net.pool.station.core.features.station.resource.controller.websocket.models.StationResourceRequest;
import net.pool.station.core.features.station.resource.controller.websocket.models.StationResourceRequestMapper;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
public class StationResourceWebSocketController {
    StationResourceUseCase stationResourceUseCase;

    StationResourceRequestMapper mapper;

    @MessageMapping("/resource")
    public void receiveResource(@Payload StationResourceRequest request, @Header("x-resource-token") String token) {

    }
}
