package org.keyus.project.keyuspan.api.exception;

import lombok.NoArgsConstructor;

/**
 * @author keyus
 * @create 2019-07-30  下午4:23
 */
@NoArgsConstructor
public class FileDownloadException extends Exception {

    public FileDownloadException(String message) {
        super(message);
    }
}
