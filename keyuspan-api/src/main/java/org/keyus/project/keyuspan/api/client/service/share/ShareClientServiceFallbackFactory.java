package org.keyus.project.keyuspan.api.client.service.share;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author keyus
 * @create 2019-08-05  下午2:43
 */
@Component
public class ShareClientServiceFallbackFactory implements FallbackFactory<ShareClientService> {

    @Override
    public ShareClientService create(Throwable throwable) {
        return null;
    }
}
