package net.pool.station.core.features.payment.controller.payment.models;

import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.bootstrap.configuration.mapper.ModelMapper;
import net.pool.station.core.domain.payment.Payment;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface PaymentResponseMapper extends ModelMapper<PaymentResponse, Payment> {
}
