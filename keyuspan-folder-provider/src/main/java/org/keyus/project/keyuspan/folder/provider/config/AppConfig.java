package org.keyus.project.keyuspan.folder.provider.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author keyus
 * @create 2019-08-08  下午2:40
 */
@Configuration
public class AppConfig {

    @Bean(name = "folderProviderExecutor")
    public ThreadPoolExecutor getExecutors () {
        return new ThreadPoolExecutor(50, 100,
                10, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1024));
    }
}
