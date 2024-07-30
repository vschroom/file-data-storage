package com.chernov.core;

import com.chernov.Contract;
import com.chernov.api.DirectoryFileStorage;

public class DirectoryFileStorageFactory {

    public static Contract create(DirectoryFileStorageProperties directoryFileStorageProperties) {
        return () -> new DirectoryFileStorage(new FileSystemServiceImpl(directoryFileStorageProperties));
    }
}
