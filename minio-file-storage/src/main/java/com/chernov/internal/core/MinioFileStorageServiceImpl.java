package com.chernov.internal.core;

import com.chernov.Attachment;
import com.chernov.internal.exceptions.MinioFileStorageException;
import io.minio.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static java.lang.String.format;

@RequiredArgsConstructor
public class MinioFileStorageServiceImpl implements MinioFileStorageService {

    private final MinioClient minioClient;

    @Override
    public void putObject(@NonNull Attachment attachment, @NonNull String bucket) {
        try (var bw = attachment.getContent()) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .object(attachment.getId())
                            .stream(bw, -1, 5 * (long) Math.pow(1024, 2))
                            .bucket(bucket)
                            .userMetadata(attachment.getMetadata())
                            .build());
        } catch (Exception ex) {
            throw new MinioFileStorageException(
                    format("Some problem while put object with bucket=%s", bucket), ex);
        }
    }

    @Override
    public boolean bucketExists(@NonNull String bucket) {
        try {
            return minioClient.bucketExists(BucketExistsArgs.builder()
                    .bucket(bucket)
                    .build());
        } catch (Exception ex) {
            throw new MinioFileStorageException(
                    format("Some problem while check bucket=%s existence", bucket), ex);
        }
    }

    @Override
    public void makeBucketIfNotExists(@NonNull String bucket) {
        var exists = this.bucketExists(bucket);
        if (!exists) {
            try {
                minioClient.makeBucket(MakeBucketArgs.builder()
                        .bucket(bucket)
                        .build());
            } catch (Exception ex) {
                throw new MinioFileStorageException(
                        format("Some problem while make bucket=%s", bucket), ex);
            }
        }
    }

    @Override
    public void removeObject(@NonNull String bucket) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucket)
                            .build());
        } catch (Exception ex) {
            throw new MinioFileStorageException(
                    format("Some problem while remove bucket=%s", bucket), ex);
        }
    }

    @Override
    public InputStream getContent(@NonNull String bucket) {
        try {
            var getObjectArgs = GetObjectArgs.builder()
                    .object(bucket)
                    .bucket(bucket)
                    .build();

            var content = minioClient.getObject(
                    GetObjectArgs.builder()
                            .object(bucket)
                            .bucket(bucket)
                            .build()
            ).readAllBytes();

            return new ByteArrayInputStream(content);
        } catch (Exception ex) {
            throw new MinioFileStorageException(
                    format("Some problem while getting object by bucket=%s", bucket), ex);
        }
    }
}
