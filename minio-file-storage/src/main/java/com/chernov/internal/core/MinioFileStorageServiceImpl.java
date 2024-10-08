package com.chernov.internal.core;

import com.chernov.Attachment;
import com.chernov.internal.exceptions.MinioFileStorageException;
import io.minio.*;
import io.minio.errors.ErrorResponseException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.Map;

import static java.lang.String.format;

@Slf4j
@RequiredArgsConstructor
public class MinioFileStorageServiceImpl implements MinioFileStorageService {

    private static final String NO_SUCH_KEY_ERROR_CODE = "NoSuchKey";

    private final MinioClient minioClient;
    private final String bucket;

    @Override
    public void putObject(@NonNull String id, @NonNull Attachment attachment) {
        try (var bw = attachment.getContent()) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .object(id)
                            .stream(bw, bw.available(), -1)
                            .bucket(bucket)
                            .userMetadata(attachment.getMetadata())
                            .build());
        } catch (Exception ex) {
            throw new MinioFileStorageException(
                    format("Some problem while put object with bucket=%s", bucket), ex);
        }
    }

    @Override
    public boolean hasObject(@NonNull String id) {
        try {
            var objectResponse = minioClient.statObject(StatObjectArgs.builder()
                    .bucket(bucket)
                    .object(id)
                    .build());

            return objectResponse != null;
        } catch (ErrorResponseException ex) {
            if (NO_SUCH_KEY_ERROR_CODE.equals(ex.errorResponse().code())) {
                return false;
            }
            throw new MinioFileStorageException(
                    format("Some problem while put object=%s with bucket=%s", id, bucket), ex);
        } catch (Exception ex) {
            throw new MinioFileStorageException(
                    format("Some problem while put object=%s with bucket=%s", id, bucket), ex);
        }
    }

    @Override
    public void removeObject(@NonNull String id) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucket)
                            .object(id)
                            .build());
        } catch (Exception ex) {
            throw new MinioFileStorageException(
                    format("Some problem while remove object=%s with bucket=%s", id, bucket), ex);
        }
    }

    @Override
    public InputStream getContent(@NonNull String id) {
        try {
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .object(id)
                            .bucket(bucket)
                            .build()
            );
        } catch (Exception ex) {
            throw new MinioFileStorageException(
                    format("Some problem while getting object=%s by bucket=%s", id, bucket), ex);
        }
    }

    @Override
    public Map<String, String> getUserMetadata(String id) {
        try {
            return minioClient.statObject(
                            StatObjectArgs.builder()
                                    .bucket(bucket)
                                    .object(id)
                                    .build())
                    .userMetadata();
        } catch (Exception ex) {
            throw new MinioFileStorageException(
                    format("Some problem while getting user metadata for object=%s with bucket=%s", id, bucket), ex);
        }
    }

    public void makeBucketIfNotExists() {
        var exists = this.hasBucket(bucket);
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

    private boolean hasBucket(String bucket) {
        try {
            return minioClient.bucketExists(BucketExistsArgs.builder()
                    .bucket(bucket)
                    .build());
        } catch (Exception ex) {
            throw new MinioFileStorageException(
                    format("Some problem while make bucket=%s", bucket), ex);
        }
    }
}
