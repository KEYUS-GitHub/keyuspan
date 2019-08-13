package org.keyus.project.keyuspan.api.client.service.file;

import feign.hystrix.FallbackFactory;
import org.keyus.project.keyuspan.api.enums.ErrorMessageEnum;
import org.keyus.project.keyuspan.api.po.FileModel;
import org.keyus.project.keyuspan.api.util.ServerResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author keyus
 * @create 2019-07-29  下午1:50
 */
@Component
public class FileModelClientServiceFallbackFactory implements FallbackFactory<FileClientService> {

    @Override
    public FileClientService create(Throwable throwable) {
        return new FileClientService() {
            @Override
            public String uploadFile(MultipartFile file) throws Throwable {
                throw throwable;
            }

            @Override
            public String[] uploadFiles(List<MultipartFile> files) throws Throwable {
                throw throwable;
            }

            @Override
            public ServerResponse<FileModel> findById(Long id) {
                return ServerResponse.createByErrorWithMessage(ErrorMessageEnum.FILE_NOT_EXIST.getMessage());
            }

            @Override
            public ServerResponse<List<FileModel>> getFilesByFolderId(Long id) {
                return ServerResponse.createByErrorWithMessage(ErrorMessageEnum.FILE_NOT_EXIST.getMessage());
            }

            @Override
            public ServerResponse<FileModel> saveFile(FileModel fileModel) {
                return ServerResponse.createByErrorWithMessage(ErrorMessageEnum.SAVE_FAIL_EXCEPTION.getMessage());
            }

            @Override
            public ServerResponse<List<FileModel>> saveFiles(List<FileModel> list) {
                return ServerResponse.createByErrorWithMessage(ErrorMessageEnum.SAVE_FAIL_EXCEPTION.getMessage());
            }

            @Override
            public String getWebServerUrl() throws Throwable {
                throw throwable;
            }

            @Override
            public ServerResponse<List<FileModel>> findAll(FileModel fileModel) {
                return ServerResponse.createByErrorWithMessage(ErrorMessageEnum.FILE_NOT_EXIST.getMessage());
            }

            @Override
            public ServerResponse<List<FileModel>> findByIdIn(Iterable<Long> iterable) {
                return ServerResponse.createByErrorWithMessage(ErrorMessageEnum.FILE_NOT_EXIST.getMessage());
            }

            @Override
            public ServerResponse<List<FileModel>> deleteFilesInRecycleBin(Long memberId) {
                return ServerResponse.createByErrorWithMessage(ErrorMessageEnum.DELETE_FAIL_EXCEPTION.getMessage());
            }
        };
    }
}
