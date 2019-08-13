package org.keyus.project.keyuspan.api.client.service.common;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author keyus
 * @create 2019-07-29  下午2:39
 */
@Component
public class CommonClientServiceFallbackFactory implements FallbackFactory<CommonClientService> {

    @Override
    public CommonClientService create(Throwable throwable) {
        return () -> {
            throw throwable;
        };
    }
}
