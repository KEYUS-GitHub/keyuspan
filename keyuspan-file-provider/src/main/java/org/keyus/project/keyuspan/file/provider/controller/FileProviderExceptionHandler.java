package org.keyus.project.keyuspan.file.provider.controller;

import org.keyus.project.keyuspan.api.util.ServerResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

/**
 * @author keyus
 * @create 2019-08-12  上午10:03
 */
@ControllerAdvice
public class FileProviderExceptionHandler {

    @ExceptionHandler(IOException.class)
    public ServerResponse handlerIOException (IOException e) {
        return ServerResponse.createByErrorWithMessage(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ServerResponse handlerException (Exception e) {
        return ServerResponse.createByErrorWithMessage(e.getMessage());
    }
}
