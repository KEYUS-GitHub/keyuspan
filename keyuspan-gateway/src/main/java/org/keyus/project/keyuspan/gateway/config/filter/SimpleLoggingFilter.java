package org.keyus.project.keyuspan.gateway.config.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author keyus
 * @create 2019-07-21  下午10:23
 */
@Slf4j
@Component
public class SimpleLoggingFilter implements GatewayFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("Method:{} Host:{} Path:{} QueryParams:{}",
                exchange.getRequest().getMethod(),
                exchange.getRequest().getURI().getHost(),
                exchange.getRequest().getURI().getPath(),
                exchange.getRequest().getQueryParams());
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
