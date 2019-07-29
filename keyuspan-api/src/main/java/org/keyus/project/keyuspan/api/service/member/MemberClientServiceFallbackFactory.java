package org.keyus.project.keyuspan.api.service.member;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author keyus
 * @create 2019-07-29  下午2:26
 */
@Component
public class MemberClientServiceFallbackFactory implements FallbackFactory<MemberClientService> {

    @Override
    public MemberClientService create(Throwable throwable) {
        return null;
    }
}
