package org.keyus.project.keyuspan.folder.provider.config;

import org.keyus.project.keyuspan.api.interceptor.SessionCheckInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author keyus
 * @create 2019-07-30  下午1:48
 */
@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

    @Bean
    public SessionCheckInterceptor sessionCheckInterceptor () {
        return new SessionCheckInterceptor();
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        // 加入拦截器的顺序决定执行拦截的顺序，先加入的先执行
        registry.addInterceptor(sessionCheckInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}
