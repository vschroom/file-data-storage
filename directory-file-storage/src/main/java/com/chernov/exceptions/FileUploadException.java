package com.chernov.exceptions;

public class FileUploadException extends RuntimeException {

    public FileUploadException(String path, Throwable ex) {
        super("Error while file with path=%s upload".formatted(path), ex);

    }
}