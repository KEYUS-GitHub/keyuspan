package org.keyus.project.keyuspan.common.provider.config;

import org.keyus.project.keyuspan.api.util.VerificationCodePool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author keyus
 * @create 2019-07-18  下午9:40
 */
@Configuration
public class AppConfig {

    @Bean
    public VerificationCodePool verificationCodePool() {
        return new VerificationCodePool();
    }
 }
