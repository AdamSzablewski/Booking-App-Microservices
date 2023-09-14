package com.adamszablewski;

import com.adamszablewski.security.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;

@AllArgsConstructor
@Configuration
public class AuthenticationFilter {


    private final JwtUtil jwtUtil;

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public GlobalFilter customGlobalFilter(AuthenticationFilter authenticationFilter) {
        return (exchange, chain) -> {

            ServerHttpRequest request;

            if (isRegisterDtoRequest(exchange.getRequest())) {
                return chain.filter(exchange);
            }
            if (isLoginRequest(exchange.getRequest())) {
                return chain.filter(exchange);

            }
            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                throw new RuntimeException("no token");
            }

            String token = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            if (token != null && token.startsWith("Bearer ")){
                token = token.substring(7);
            }
            try {
                jwtUtil.validateToken(token);

                 request = exchange.getRequest()
                        .mutate()
                        .header("userEmail", jwtUtil.getUsernameFromJWT(token))
                        .build();

            }catch (Exception e ){
                e.printStackTrace();
                throw new RuntimeException("Security service not available");
            }

            return chain.filter(exchange.mutate().request(request).build());
        };

    }

    private boolean isLoginRequest(ServerHttpRequest request) {
        String path = request.getPath().toString();
        return path.contains("/login");
    }

    private boolean isRegisterDtoRequest(ServerHttpRequest request) {
        String path = request.getPath().toString();
        return path.contains("/register");
    }

}
