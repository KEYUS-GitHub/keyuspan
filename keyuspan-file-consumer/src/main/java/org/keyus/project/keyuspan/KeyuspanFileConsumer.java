package org.keyus.project.keyuspan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author keyus
 * @create 2019-08-04  下午3:22
 */
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients
@EnableTransactionManagement
@EnableRedisHttpSession
public class KeyuspanFileConsumer {

    public static void main(String[] args) {
        SpringApplication.run(KeyuspanFileConsumer.class, args);
    }
}
