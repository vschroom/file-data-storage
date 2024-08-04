package com.chernov.internal.core;

import com.chernov.Attachment;
import com.chernov.internal.exceptions.MinioFileStorageException;
import io.minio.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;

import static java.lang.String.format;

@Slf4j
@RequiredArgsConstructor
public class MinioFileStorageServiceImpl implements MinioFileStorageService {

    private final MinioClient minioClient;
    private final String bucket;

    @Override
    public void putObject(@NonNull Attachment attachment) {
        makeBucketIfNotExists(bucket);
        try (var bw = attachment.getContent()) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .object(attachment.getId())
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
        } catch (Exception ex) {
            log.error("Some problem while put object={} with bucket={}", id, bucket, ex);
            // FIXME получается что мы словили ошибку но как ни в чем не бывало продолжаем? или я неверно понял?
            return false;
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
            var content = minioClient.getObject(
                    GetObjectArgs.builder()
                            .object(id)
                            .bucket(bucket)
                            .build()
            ).readAllBytes(); // FIXME а зачем ты это делаешь?

            return new ByteArrayInputStream(content);
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

    private void makeBucketIfNotExists(@NonNull String bucket) {
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
