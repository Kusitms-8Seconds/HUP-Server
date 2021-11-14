package eightseconds.global.config.websocket;

import eightseconds.global.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Order(Ordered.HIGHEST_PRECEDENCE + 99)
@RequiredArgsConstructor
@Component
public class FilterChannelInterceptor implements ChannelInterceptor {

    private final TokenProvider tokenProvider;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
        if (StompCommand.CONNECT.equals(headerAccessor.getCommand()) || StompCommand.SEND.equals(headerAccessor.getCommand())
                || StompCommand.SUBSCRIBE.equals(headerAccessor.getCommand())) {
                String jwt = headerAccessor.getNativeHeader("Authorization").toString();
                if (StringUtils.hasText(jwt) && jwt.startsWith("[Bearer")) {
                    jwt = jwt.substring(8, jwt.length()-1);
                }
            tokenProvider.validateToken(jwt);
            return message;
        }
        return message;
    }
}