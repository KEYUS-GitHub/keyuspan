package org.keyus.project.keyuspan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author keyus
 * @create 2019-07-18  下午9:35
 */
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@EnableRedisHttpSession
public class KeyuspanCommonProvider {

    public static void main(String[] args) {
        SpringApplication.run(KeyuspanCommonProvider.class, args);
    }
}
