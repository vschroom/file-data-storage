package com.chernov.internal.core;

import com.chernov.MinioFileStorageProperties;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MinioClientConfig {

    private final MinioFileStorageProperties minioFileStorageProperties;

    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(minioFileStorageProperties.getHost())
                .credentials(
                        minioFileStorageProperties.getUsername(),
                        minioFileStorageProperties.getPassword()
                )
                .build();
    }
}
