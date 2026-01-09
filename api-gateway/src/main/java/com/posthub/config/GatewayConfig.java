package com.posthub.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(predicateSpec -> predicateSpec
                        .path("/login")
                        .and()
                        .method(HttpMethod.POST)
                        .filters(f -> f
                                .setPath("/auth/login")

                                .filter((exchange, chain) -> {
                                    System.out.println("Custom Log: Redirecting to IAM Service. Path: " + exchange.getRequest().getPath());

                                    return chain.filter(exchange).then(
                                            Mono.fromRunnable(() -> {
                                                System.out.println("Custom Log: Response status code: " + exchange.getResponse().getStatusCode());
                                            })
                                    );
                                })

                        )
                        .uri("http://localhost:8189")
                )
                .build();
    }
}
