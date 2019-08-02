package org.keyus.project.keyuspan.api.client.service.folder;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author keyus
 * @create 2019-08-02  下午3:14
 */
@Component
public class FolderClientServiceFallbackFactory implements FallbackFactory<FolderClientService> {

    @Override
    public FolderClientService create(Throwable throwable) {
        return null;
    }
}
