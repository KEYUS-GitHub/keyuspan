package org.keyus.project.keyuspan.api.client.service.share;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author keyus
 * @create 2019-08-05  下午2:42
 */
@FeignClient(value = "keyuspan-share-provider", fallbackFactory = ShareClientServiceFallbackFactory.class)
public interface ShareClientService {


}
