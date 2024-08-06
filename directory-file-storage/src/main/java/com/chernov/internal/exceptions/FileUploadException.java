package com.chernov.internal.exceptions;

import com.chernov.FileStorageException;

import static java.lang.String.format;

public class FileUploadException extends FileStorageException {

    public FileUploadException(String path, Throwable ex) {
        super(format("Error while file with path=%s upload", path), ex);

    }
}