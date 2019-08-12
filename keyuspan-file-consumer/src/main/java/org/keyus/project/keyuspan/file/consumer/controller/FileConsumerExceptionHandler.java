package org.keyus.project.keyuspan.file.consumer.controller;

import org.keyus.project.keyuspan.api.enums.ErrorMessageEnum;
import org.keyus.project.keyuspan.api.exception.FileDownloadException;
import org.keyus.project.keyuspan.api.util.ServerResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;

/**
 * @author keyus
 * @create 2019-08-11  下午7:16
 */
@ControllerAdvice
@ResponseBody
public class FileConsumerExceptionHandler {

    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.GATEWAY_TIMEOUT)
    public ServerResponse handlerIOException (IOException e) {
        return ServerResponse.createByErrorWithMessage(ErrorMessageEnum.NETWORK_EXCEPTION.getMessage());
    }

    @ExceptionHandler(FileDownloadException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ServerResponse handlerFileDownloadException (FileDownloadException e) {
        return ServerResponse.createByErrorWithMessage(ErrorMessageEnum.FILE_DOWNLOAD_EXCEPTION.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ServerResponse handlerException (Exception e) {
        return ServerResponse.createByErrorWithMessage(ErrorMessageEnum.SYSTEM_EXCEPTION.getMessage());
    }
}
