package com.chernov.internal.exceptions;

public class MinioFileStorageException extends RuntimeException {

    public MinioFileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
