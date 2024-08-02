package com.chernov;

import com.chernov.internal.core.MinioClientConfig;
import com.chernov.internal.core.MinioFileStorageApi;
import com.chernov.internal.core.MinioFileStorageServiceImpl;

public class MinioFileStorageFactory {

    public static Contract create(MinioFileStorageProperties minioFileStorageProperties) {
        return () -> new MinioFileStorageApi(new MinioFileStorageServiceImpl(
                new MinioClientConfig(minioFileStorageProperties).minioClient()));
    }
}
