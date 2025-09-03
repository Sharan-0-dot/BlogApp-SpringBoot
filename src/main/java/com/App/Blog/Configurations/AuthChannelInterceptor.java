package com.App.Blog.Configurations;

import com.App.Blog.Service.JWT_Service;
import com.App.Blog.Service.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Slf4j
public class AuthChannelInterceptor implements ChannelInterceptor {

    @Autowired
    private JWT_Service jwtService;

    @Autowired
    private ApplicationContext context;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            List<String> authHeaders = accessor.getNativeHeader("Authorization");
            String token = null;
            if (authHeaders != null && !authHeaders.isEmpty()) {
                String header = authHeaders.get(0);
                if (header.startsWith("Bearer ")) token = header.substring(7);
            } else {
                List<String> tokens = accessor.getNativeHeader("token");
                if (tokens != null && !tokens.isEmpty()) token = tokens.get(0);
            }

            if (token != null) {
                try {
                    String username = jwtService.extractUserName(token);
                    UserDetails userDetails = context.getBean(UserDetailsImpl.class).loadUserByUsername(username);
                    if (jwtService.validateToken(token, userDetails)) {
                        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        accessor.setUser(auth);
                    }
                } catch (Exception e) {
                    log.error(e.getLocalizedMessage());
                }
            }
        }
        return message;
    }
}
