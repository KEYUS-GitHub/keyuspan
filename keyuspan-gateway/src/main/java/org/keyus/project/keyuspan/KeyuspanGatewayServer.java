package org.keyus.project.keyuspan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author keyus
 * @create 2019-07-20  上午10:13
 */
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
public class KeyuspanGatewayServer {

    public static void main(String[] args) {
        SpringApplication.run(KeyuspanGatewayServer.class, args);
    }

}