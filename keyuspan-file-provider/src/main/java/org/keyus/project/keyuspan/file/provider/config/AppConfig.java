package org.keyus.project.keyuspan.file.provider.config;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.keyus.project.keyuspan.api.interceptor.SessionCheckInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.jmx.support.RegistrationPolicy;

/**
 * @author keyus
 * @create 2019-07-23  上午10:43
 */
@Configuration
@Import(FdfsClientConfig.class)
// 解决jmx重复注册bean的问题
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
public class AppConfig {

    @Bean
    public SessionCheckInterceptor sessionCheckInterceptor() {
        return new SessionCheckInterceptor();
    }

}
