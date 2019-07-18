package org.keyus.project.keyuspan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author keyus
 * @create 2019-07-18  下午9:35
 */
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
public class KeyuspanCommonProvider {

    public static void main(String[] args) {
        SpringApplication.run(KeyuspanCommonProvider.class, args);
    }
}
