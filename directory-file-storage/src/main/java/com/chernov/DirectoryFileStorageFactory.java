package com.chernov;

import com.chernov.internal.core.DirectoryFileStorage;
import com.chernov.internal.core.FileSystemServiceImpl;
import com.chernov.internal.core.StorageType;
import com.chernov.internal.core.ZipFileSystemServiceImpl;

public class DirectoryFileStorageFactory {

    public static Contract create(DirectoryFileStorageProperties directoryFileStorageProperties) {
        var fileSystemService = directoryFileStorageProperties.getStorageType() == StorageType.ZIP
                ? new ZipFileSystemServiceImpl(directoryFileStorageProperties)
                : new FileSystemServiceImpl(directoryFileStorageProperties);

        return () -> new DirectoryFileStorage(
                fileSystemService,
                directoryFileStorageProperties.getGeneratorTypeId(),
                directoryFileStorageProperties.getGeneratorIdService());
    }
}
