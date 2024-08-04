package com.chernov;

public class DirectoryStorageConfig {

    public static FileStorageApi fileStorageApi() {
        return FileStorageApiFactory.create(
                DirectoryFileStorageFactory.create(new DirectoryFileStorageProperties("custom-dir")));
    }
}
