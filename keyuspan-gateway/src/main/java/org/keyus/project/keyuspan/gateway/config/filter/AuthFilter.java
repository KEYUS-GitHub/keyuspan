package org.keyus.project.keyuspan.gateway.config.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.keyus.project.keyuspan.api.enums.ErrorMessageEnum;
import org.keyus.project.keyuspan.api.enums.HttpHeaderValueEnum;
import org.keyus.project.keyuspan.api.util.ServerResponse;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * @author keyus
 * @create 2019-08-13  上午10:32
 * 实现权限验证
 */
@Slf4j
@Component
public class AuthFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获取token
        String token = exchange.getRequest().getQueryParams().getFirst("token");
        // 如果没有token的值
        if (Objects.isNull(token) || token.isEmpty()) {
            // 给予客户端没有权限的响应
            ServerHttpResponse response = exchange.getResponse();
            ServerResponse<Object> responseDate = ServerResponse.createByErrorWithMessage(ErrorMessageEnum.UNAUTHORIZED.getMessage());
            try {
                // 转换ServerResponse对象为JSON格式
                ObjectMapper mapper = new ObjectMapper();
                byte[] date = mapper.writeValueAsBytes(responseDate);
                // 输出信息到错误页面
                DataBuffer buffer = response.bufferFactory().wrap(date);
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                response.getHeaders().add(HttpHeaders.CONTENT_TYPE, HttpHeaderValueEnum.APPLICATION_JSON.getValue());
                return response.writeWith(Mono.just(buffer));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
