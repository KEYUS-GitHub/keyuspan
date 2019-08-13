package org.keyus.project.keyuspan.api.client.service.folder;

import feign.hystrix.FallbackFactory;
import org.keyus.project.keyuspan.api.enums.ErrorMessageEnum;
import org.keyus.project.keyuspan.api.po.VirtualFolder;
import org.keyus.project.keyuspan.api.util.ServerResponse;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author keyus
 * @create 2019-08-02  下午3:14
 */
@Component
public class FolderClientServiceFallbackFactory implements FallbackFactory<FolderClientService> {

    @Override
    public FolderClientService create(Throwable throwable) {
        return new FolderClientService() {
            @Override
            public ServerResponse<VirtualFolder> createMainFolder(Long memberId) {
                return ServerResponse.createByErrorWithMessage(ErrorMessageEnum.CREATE_MAIN_FOLDER_EXCEPTION.getMessage());
            }

            @Override
            public ServerResponse<VirtualFolder> findById(Long id) {
                return ServerResponse.createByErrorWithMessage(ErrorMessageEnum.FOLDER_NOT_EXIST.getMessage());
            }

            @Override
            public ServerResponse<List<VirtualFolder>> findByIdIn(Iterable<Long> iterable) {
                return ServerResponse.createByErrorWithMessage(ErrorMessageEnum.FOLDER_NOT_EXIST.getMessage());
            }

            @Override
            public ServerResponse<List<VirtualFolder>> findAll(VirtualFolder virtualFolder) {
                return ServerResponse.createByErrorWithMessage(ErrorMessageEnum.FOLDER_NOT_EXIST.getMessage());
            }

            @Override
            public ServerResponse<VirtualFolder> save(VirtualFolder virtualFolder) {
                return ServerResponse.createByErrorWithMessage(ErrorMessageEnum.SAVE_FAIL_EXCEPTION.getMessage());
            }

            @Override
            public ServerResponse<List<VirtualFolder>> saveAll(List<VirtualFolder> virtualFolders) {
                return ServerResponse.createByErrorWithMessage(ErrorMessageEnum.SAVE_FAIL_EXCEPTION.getMessage());
            }

            @Override
            public ServerResponse<List<VirtualFolder>> deleteFoldersInRecycleBin() {
                return ServerResponse.createByErrorWithMessage(ErrorMessageEnum.DELETE_FAIL_EXCEPTION.getMessage());
            }

            @Override
            public ServerResponse<String> getVirtualPath(Long id) {
                return ServerResponse.createByErrorWithMessage(ErrorMessageEnum.FOLDER_NOT_EXIST.getMessage());
            }
        };
    }
}
