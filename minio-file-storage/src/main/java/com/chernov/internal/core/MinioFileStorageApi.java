package com.chernov.internal.core;

import com.chernov.Attachment;
import com.chernov.FileAttachment;
import com.chernov.internal.api.InternalFileStorageApi;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static java.util.Optional.of;

@RequiredArgsConstructor
public class MinioFileStorageApi implements InternalFileStorageApi {

    private final MinioFileStorageService minioFileStorageService;

    @Override
    public String store(@NonNull Attachment attachment) {
        minioFileStorageService.putObject(attachment);

        return attachment.getId();
    }

    @Override
    public boolean exists(@NonNull String id) {
        return minioFileStorageService.hasObject(id);
    }

    @Override
    public boolean remove(@NonNull String id) {
        minioFileStorageService.removeObject(id);

        return true;
    }

    @Override
    public Optional<Attachment> findBy(@NonNull String id) {
        var content = minioFileStorageService.getContent(id);
        var userMetadata = minioFileStorageService.getUserMetadata(id);

        return of(new FileAttachment(id, content, userMetadata));
    }
}
