package org.keyus.project.keyuspan.api.client.service.share;

import feign.hystrix.FallbackFactory;
import org.keyus.project.keyuspan.api.enums.ErrorMessageEnum;
import org.keyus.project.keyuspan.api.po.ShareRecord;
import org.keyus.project.keyuspan.api.util.ServerResponse;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author keyus
 * @create 2019-08-05  下午2:43
 */
@Component
public class ShareClientServiceFallbackFactory implements FallbackFactory<ShareClientService> {

    @Override
    public ShareClientService create(Throwable throwable) {
        return new ShareClientService() {
            @Override
            public ServerResponse<ShareRecord> save(ShareRecord record) {
                return ServerResponse.createByErrorWithMessage(ErrorMessageEnum.SAVE_FAIL_EXCEPTION.getMessage());
            }

            @Override
            public ServerResponse<ShareRecord> findByUrl(String url) {
                return ServerResponse.createByErrorWithMessage(ErrorMessageEnum.SHARE_RECORD_NOT_EXIST.getMessage());
            }

            @Override
            public ServerResponse<ShareRecord> findById(Long id) {
                return ServerResponse.createByErrorWithMessage(ErrorMessageEnum.SHARE_RECORD_NOT_EXIST.getMessage());
            }

            @Override
            public ServerResponse<List<ShareRecord>> findAll(ShareRecord record) {
                return ServerResponse.createByErrorWithMessage(ErrorMessageEnum.SHARE_RECORD_NOT_EXIST.getMessage());
            }

            @Override
            public void deleteInBatch(Iterable<ShareRecord> records) throws Throwable {
                throw throwable;
            }
        };
    }
}
