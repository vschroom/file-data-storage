package com.chernov.internal.exceptions;

import com.chernov.IoFileStorageException;

public class IoFileMetadataReadException extends IoFileStorageException {

    public IoFileMetadataReadException(String path, Throwable ex) {
        super(path, ex);
    }
}
