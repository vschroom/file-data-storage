package com.chernov;

import com.chernov.internal.core.DirectoryFileStorage;
import com.chernov.internal.core.FileSystemServiceImpl;

public class DirectoryFileStorageFactory {

    //FIXME в эанном классе не прохо было бы сделать инициализацию
    public static Contract create(DirectoryFileStorageProperties directoryFileStorageProperties) {
        return () -> new DirectoryFileStorage(new FileSystemServiceImpl(directoryFileStorageProperties));
    }
}
