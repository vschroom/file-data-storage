package com.chernov.internal.exceptions;

import com.chernov.IoFileStorageException;

import static java.lang.String.format;

public class IoFileUploadException extends IoFileStorageException {

    public IoFileUploadException(String path, Throwable ex) {
        super(format("Error while file with path=%s upload", path), ex);
    }
}