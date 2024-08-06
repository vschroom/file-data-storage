package com.chernov.internal.exceptions;

import com.chernov.FileStorageException;

public class MinioFileStorageException extends FileStorageException {

    public MinioFileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
