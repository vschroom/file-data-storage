package com.chernov.internal.core;

import com.chernov.*;
import com.chernov.internal.api.InternalFileStorageApi;
import com.chernov.internal.api.UuidComponent;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class MinioFileStorageApi implements InternalFileStorageApi {

    private static final String INITIAL_FILENAME = "initial_filename";
    private static final String INITIAL_EXTENSION = "initial_extension";
    private final MinioFileStorageService minioFileStorageService;
    private final GeneratorTypeId generatorTypeId;
    private final GeneratorIdService generatorIdService;

    @Override
    public String store(@NonNull Attachment attachment) {
        var id = defineAttachmentId(attachment);
        addDefaultMetadata(attachment.getMetadata(), attachment);
        minioFileStorageService.putObject(id, attachment);

        return id;
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
    public Attachment findBy(@NonNull String id) {
        var content = minioFileStorageService.getContent(id);
        var userMetadata = minioFileStorageService.getUserMetadata(id);
        var filename = userMetadata.get(INITIAL_FILENAME);
        var extension = userMetadata.get(INITIAL_EXTENSION);

        return new FileAttachment(id, content, userMetadata, filename, extension);
    }

    private void addDefaultMetadata(Map<String, String> metadata, Attachment attachment) {
        metadata.put(INITIAL_FILENAME, attachment.getFilename());
        metadata.put(INITIAL_EXTENSION, attachment.getFileExtension());
    }

    private String defineAttachmentId(Attachment attachment) {
        switch (this.generatorTypeId) {
            case CUSTOM_GENERATOR:
                return generatorIdService.generateId();
            case LIB_GENERATOR:
                return new DefaultGeneratorIdService(new UuidComponent()).generateId();
            default:
                return attachment.getId();
        }
    }
}
