package org.keyus.project.keyuspan.folder.consumer.config;

import com.codingapi.tx.config.service.TxManagerTxUrlService;
import org.keyus.project.keyuspan.api.lcn.service.TxManagerTxUrlServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author keyus
 * @create 2019-08-14  下午2:44
 */
@Configuration
public class AppConfig {

    @Bean
    public TxManagerTxUrlService txUrlService() {
        return new TxManagerTxUrlServiceImpl();
    }
}
