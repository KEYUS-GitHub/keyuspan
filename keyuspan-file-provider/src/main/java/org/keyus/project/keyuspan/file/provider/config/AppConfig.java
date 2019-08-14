package org.keyus.project.keyuspan.file.provider.config;

import com.codingapi.tx.config.service.TxManagerTxUrlService;
import com.github.tobato.fastdfs.FdfsClientConfig;
import org.keyus.project.keyuspan.api.lcn.service.TxManagerTxUrlServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.jmx.support.RegistrationPolicy;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author keyus
 * @create 2019-07-23  上午10:43
 */
@Configuration
@Import(FdfsClientConfig.class)
// 解决jmx重复注册bean的问题
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
public class AppConfig {

    @Bean(name = "fileProviderExecutor")
    public ThreadPoolExecutor getExecutors () {
        return new ThreadPoolExecutor(50, 100,
                10, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1024));
    }

    @Bean
    public TxManagerTxUrlService txUrlService() {
        return new TxManagerTxUrlServiceImpl();
    }

}
