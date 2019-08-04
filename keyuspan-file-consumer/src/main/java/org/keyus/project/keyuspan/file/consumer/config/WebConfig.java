package org.keyus.project.keyuspan.file.consumer.config;

import org.keyus.project.keyuspan.api.interceptor.SessionCheckInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author keyus
 * @create 2019-08-04  下午11:17
 */
@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

    @Bean
    public SessionCheckInterceptor sessionCheckInterceptor() {
        return new SessionCheckInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionCheckInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}
