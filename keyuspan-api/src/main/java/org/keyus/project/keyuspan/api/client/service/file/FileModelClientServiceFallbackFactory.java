package org.keyus.project.keyuspan.api.client.service.file;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author keyus
 * @create 2019-07-29  下午1:50
 */
@Component
public class FileModelClientServiceFallbackFactory implements FallbackFactory<FileClientService> {

    @Override
    public FileClientService create(Throwable throwable) {
        return null;
    }
}
