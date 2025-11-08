package net.pool.station.core.features.payment.controller.webhook.models;

import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.bootstrap.configuration.mapper.ModelMapper;
import net.pool.station.core.domain.payment.PaymentWebhook;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface PaymentWebhookRequestMapper extends ModelMapper<PaymentWebhookRequest.Data, PaymentWebhook> {
}
