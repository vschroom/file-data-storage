package com.chernov;

import com.chernov.internal.core.MinioClientConfig;
import com.chernov.internal.core.MinioFileStorageApi;
import com.chernov.internal.core.MinioFileStorageServiceImpl;

public class MinioFileStorageFactory {

    public static Contract create(MinioFileStorageProperties minioFileStorageProperties) {
        var minioClientConfig = new MinioClientConfig(minioFileStorageProperties);

        return () -> new MinioFileStorageApi(new MinioFileStorageServiceImpl(
                minioClientConfig.minioClient(), minioClientConfig.getBucket()));
    }
}
