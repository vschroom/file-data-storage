package com.chernov.internal.exceptions;

import com.chernov.IoFileStorageException;

import static java.lang.String.format;

public class IoFileReadException extends IoFileStorageException {

    public IoFileReadException(String path, Throwable ex) {
        super(format("Error while read file: %s", path), ex);
    }
}
