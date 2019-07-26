package org.keyus.project.keyuspan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author keyus
 * @create 2019-07-17  下午10:50
 */
@SpringBootApplication
@EnableRedisHttpSession
public class KeyuspanMailServer {

    public static void main(String[] args) {
        SpringApplication.run(KeyuspanMailServer.class, args);
    }
}
