package com.chernov.internal.exceptions;

import static java.lang.String.format;

public class FileUploadException extends FileStorageException {

    public FileUploadException(String id, Throwable ex) {
        super(format("Error while file with id=%s upload", id), ex);
    }
}
