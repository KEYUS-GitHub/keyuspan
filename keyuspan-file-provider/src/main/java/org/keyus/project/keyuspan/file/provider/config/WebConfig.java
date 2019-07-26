package org.keyus.project.keyuspan.file.provider.config;

import org.keyus.project.keyuspan.api.interceptor.SessionCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author keyus
 * @create 2019-07-26  上午8:40
 */
@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

    private final SessionCheckInterceptor sessionCheckInterceptor;

    public WebConfig(SessionCheckInterceptor sessionCheckInterceptor) {
        this.sessionCheckInterceptor = sessionCheckInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionCheckInterceptor).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}
