package com.chernov;

import com.chernov.internal.core.DirectoryFileStorage;
import com.chernov.internal.core.FileSystemServiceImpl;

public class DirectoryFileStorageFactory {

    public static Contract create(DirectoryFileStorageProperties directoryFileStorageProperties) {
        return () -> new DirectoryFileStorage(new FileSystemServiceImpl(directoryFileStorageProperties));
    }
}
