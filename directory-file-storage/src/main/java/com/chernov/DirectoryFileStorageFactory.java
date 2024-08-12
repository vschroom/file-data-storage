package com.chernov;

import com.chernov.internal.core.DirectoryFileStorage;
import com.chernov.internal.core.FileSystemServiceImpl;
import com.chernov.internal.core.StorageType;
import com.chernov.internal.core.ZipFileSystemServiceImpl;

public class DirectoryFileStorageFactory {

    public static Contract create(DirectoryFileStorageProperties directoryFileStorageProperties,
                                  FileStorageIdGenerator customFileStorageIdGenerator) {
        var directory = directoryFileStorageProperties.getDirectory();
        var fileSystemService = directoryFileStorageProperties.getStorageType() == StorageType.ZIP
                ? new ZipFileSystemServiceImpl(directory)
                : new FileSystemServiceImpl(directory);
        fileSystemService.createDirectoryIfNotExists(directory);

        var fileStorageIdGenerator = FileStorageIdGeneratorFactory.create(
                directoryFileStorageProperties.getGeneratorTypeId(), customFileStorageIdGenerator);

        return () -> new DirectoryFileStorage(fileSystemService, fileStorageIdGenerator);
    }
}
