package org.keyus.project.keyuspan.folder.provider.config;

import org.keyus.project.keyuspan.api.interceptor.SessionCheckInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author keyus
 * @create 2019-07-27  下午11:08
 */
@Configuration
public class AppConfig {

    @Bean
    public SessionCheckInterceptor sessionCheckInterceptor() {
        return new SessionCheckInterceptor();
    }
}
