package net.pool.station.core.features.payment.repository.feign.models;

import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.bootstrap.configuration.mapper.ModelMapper;
import net.pool.station.core.domain.payment.Payment;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class, implementationName = "PaymentFeignResponseMapper")
public interface PaymentResponseMapper extends ModelMapper<PaymentResponse, Payment> {
}
