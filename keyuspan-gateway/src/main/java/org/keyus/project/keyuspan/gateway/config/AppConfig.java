package org.keyus.project.keyuspan.gateway.config;

import org.keyus.project.keyuspan.gateway.config.filter.SimpleLoggingFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author keyus
 * @create 2019-07-21  下午10:21
 */
@Configuration
public class AppConfig {

//    @Bean
//    public RouteLocator customerRouteLocator(RouteLocatorBuilder builder, SimpleLoggingFilter simpleLoggingFilter) {
//
//        return builder.routes()
//                .route(r -> r.path("/**")
//                .filters(f -> f.filter(simpleLoggingFilter)
//                .addResponseHeader("X-Response-Default-Foo", "Default-Bar"))
//                .uri("lb://keyuspan-common-consumer")
//                .order(0)
//                .id("consumer_filter_router"))
//                .build();
//    }
}