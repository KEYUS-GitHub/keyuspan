package org.keyus.project.keyuspan.task.config;

import com.codingapi.tx.config.service.TxManagerTxUrlService;
import org.keyus.project.keyuspan.api.lcn.service.TxManagerTxUrlServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author keyus
 * @create 2019-08-08  下午2:15
 */
@Configuration
public class AppConfig {

    @Bean(name = "taskServerExecutor")
    public ThreadPoolExecutor getExecutors () {
        return new ThreadPoolExecutor(100, 200,
                10, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1024));
    }

    @Bean
    public TxManagerTxUrlService txUrlService() {
        return new TxManagerTxUrlServiceImpl();
    }
}
