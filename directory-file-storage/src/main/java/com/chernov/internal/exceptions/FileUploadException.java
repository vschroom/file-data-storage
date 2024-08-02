package com.chernov.internal.exceptions;

import static java.lang.String.format;

public class FileUploadException extends DirectoryFileStorageException {

    public FileUploadException(String path, Throwable ex) {
        super(format("Error while file with path=%s upload", path), ex);

    }
}