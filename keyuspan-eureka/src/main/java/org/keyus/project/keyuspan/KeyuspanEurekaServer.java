package org.keyus.project.keyuspan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author keyus
 * @create 2019-07-16  下午9:19
 */
@SpringBootApplication
@EnableEurekaServer
public class KeyuspanEurekaServer {

    public static void main(String[] args) {
        SpringApplication.run(KeyuspanEurekaServer.class, args);
    }

}
