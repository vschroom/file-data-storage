package com.chernov.internal.exceptions;

public class MinioFileStorageException extends FileStorageException {

    public MinioFileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
