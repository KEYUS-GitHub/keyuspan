package org.keyus.project.keyuspan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author keyus
 * @create 2019-07-17  下午6:14
 */
@SpringBootApplication
@EnableEurekaClient
public class KeyuspanMemberConsumer {

    public static void main(String[] args) {
        SpringApplication.run(KeyuspanMemberConsumer.class, args);
    }
}
