package com.chernov.internal.core;

import com.chernov.Attachment;
import com.chernov.FileAttachment;
import com.chernov.internal.api.InternalFileStorageApi;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.Map;

@RequiredArgsConstructor
public class MinioFileStorageApi implements InternalFileStorageApi {

    private final MinioFileStorageService minioFileStorageService;

    @Override
    public void store(Attachment attachment) {
        var bucket = attachment.getId();
        minioFileStorageService.makeBucketIfNotExists(bucket);
        minioFileStorageService.putObject(attachment, bucket);
    }

    @Override
    public boolean exists(String id) {
        return minioFileStorageService.bucketExists(id);
    }

    @Override
    public boolean remove(String id) {
        minioFileStorageService.removeObject(id);

        return true;
    }

    @Override
    public Attachment findBy(String id, Collection<String> metadataKeys) {
        var content = minioFileStorageService.getContent(id);

        return new FileAttachment(id, content, Map.of(), null);
    }
}
