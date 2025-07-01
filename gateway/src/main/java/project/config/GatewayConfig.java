package project.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import project.filter.JwtAuthenticationFilter;

@Configuration
public class GatewayConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public GatewayConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {

        return builder.routes()

            .route("book", r -> r.path("/books/**")
                .filters(f -> f.filter(jwtAuthenticationFilter))
                .uri("http://localhost:8083"))
            
            .route("subscription", r -> r.path("/subscriptions/**")
                .filters(f -> f.filter(jwtAuthenticationFilter))
                .uri("http://localhost:8081"))
            
            .route("manuscript", r -> r.path("/manuscripts/**")
                .filters(f -> f.filter(jwtAuthenticationFilter))
                .uri("http://localhost:8087"))
            
            .route("point", r -> r.path("/points/**")
                .filters(f -> f.filter(jwtAuthenticationFilter))
                .uri("http://localhost:8089"))

            .build();
    }
}