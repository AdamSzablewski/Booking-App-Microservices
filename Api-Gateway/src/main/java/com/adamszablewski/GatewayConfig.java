package com.adamszablewski;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder){
        return builder.routes()
                .route("booking-service", r -> r
                        .path("/appointments/**")
                        .or()
                        .path("/facilities/**")
                        .or()
                        .path("/services/**")
                        .or()
                        .path("/timeslots/**")
                        .or()
                        .uri("lb://BOOKING"))
                .route("user-service", r -> r
                        .path("/users/**")
                        .uri("lb://USERS"))
                .route("messaging-service", r -> r
                        .path("/conversations/**")
                        .or()
                        .path("/messages/**")
                        .uri("lb://MESSAGING"))

                .route("eureka-status", r -> r
                        .path("/eureka/**")
                        .uri("http://localhost:8761"))
                .build();
    }
}
