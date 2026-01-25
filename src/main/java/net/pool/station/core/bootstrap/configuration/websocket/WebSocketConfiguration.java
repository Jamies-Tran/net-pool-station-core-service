package net.pool.station.core.bootstrap.configuration.websocket;

import lombok.extern.slf4j.Slf4j;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyAuthenticationException;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import net.pool.station.core.bootstrap.utils.MyPasswordEncoderUtils;
import net.pool.station.core.bootstrap.utils.MyTokenUtils;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.account.Account;
import net.pool.station.core.domain.account.AccountUseCase;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Slf4j
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {
    MyTokenUtils myTokenUtils;

    AccountUseCase accountUseCase;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOriginPatterns("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic", "/queue");
        registry.setApplicationDestinationPrefixes("/app");
        registry.setUserDestinationPrefix("/user");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
                if (MyObjectUtils.isEquals(accessor.getCommand(), StompCommand.CONNECT)) {

                    String token = accessor.getFirstNativeHeader("Authorization");
                    if (MyObjectUtils.isNotEmpty(token)) {
                        String userId = myTokenUtils.getUserIdentifyFromToken(token);
                        UserDetails user = accountUseCase.findByEmail(DomainKey.of(userId))
                                .map(a -> User.builder()
                                        .username(a.accountId().toString())
                                        .password(MyPasswordEncoderUtils.passwordEncoder().encode(a.password()))
                                        .disabled(false)
                                        .authorities(new SimpleGrantedAuthority("ROLE_%s"
                                                .formatted(a.role().roleCode())))
                                        .build())
                                .orElseThrow(MyAuthenticationException::new);
                        UsernamePasswordAuthenticationToken authToken = UsernamePasswordAuthenticationToken
                                .authenticated(user.getUsername(), null, user.getAuthorities());
                        accessor.setUser(authToken);
                    }

                }
                return message;
            }
        });
    }
}
