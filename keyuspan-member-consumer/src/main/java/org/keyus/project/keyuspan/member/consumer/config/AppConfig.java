package org.keyus.project.keyuspan.member.consumer.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author keyus
 * @create 2019-07-17  下午6:20
 */
@Configuration
public class AppConfig {

    // Spring Cloud Ribbon是基于Netflix Ribbon实现的一套客户端
    // 负载均衡的工具。
    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
