package org.keyus.project.keyuspan.file.consumer.controller;

import org.keyus.project.keyuspan.api.enums.ErrorMessageEnum;
import org.keyus.project.keyuspan.api.exception.FileDownloadException;
import org.keyus.project.keyuspan.api.util.ServerResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author keyus
 * @create 2019-08-11  下午7:16
 */
@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(FileDownloadException.class)
    public ServerResponse handlerFileDownloadException (FileDownloadException e) {
        return ServerResponse.createByErrorWithMessage(ErrorMessageEnum.FILE_DOWNLOAD_EXCEPTION.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ServerResponse handlerException (Exception e) {
        return ServerResponse.createByErrorWithMessage(ErrorMessageEnum.SYSTEM_EXCEPTION.getMessage());
    }
}
