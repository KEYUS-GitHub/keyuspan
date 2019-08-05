package org.keyus.project.keyuspan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author keyus
 * @create 2019-08-05  下午2:23
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableDiscoveryClient
@EnableJpaRepositories
@EnableTransactionManagement
@EnableRedisHttpSession
public class KeyuspanShareProvider {

    public static void main(String[] args) {
        SpringApplication.run(KeyuspanShareProvider.class, args);
    }
}
