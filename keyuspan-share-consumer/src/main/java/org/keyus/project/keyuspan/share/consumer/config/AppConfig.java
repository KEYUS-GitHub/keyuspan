package org.keyus.project.keyuspan.share.consumer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author keyus
 * @create 2019-08-07  下午2:20
 */
@Configuration
public class AppConfig {

    @Bean(name = "shareConsumerExecutor")
    public ThreadPoolExecutor getExecutors () {
        return new ThreadPoolExecutor(100, 200,
                10, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1024));
    }
}
