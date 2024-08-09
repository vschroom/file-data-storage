package com.chernov.internal.core;

import com.chernov.*;
import com.chernov.internal.api.InternalFileStorageApi;
import com.chernov.internal.api.UuidComponent;
import com.chernov.internal.exceptions.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.String.format;

@RequiredArgsConstructor
public class DirectoryFileStorage implements InternalFileStorageApi {

    private static final String USER_METADATA_KEY = "user";
    private static final String INITIAL_FILENAME = "initial_filename";
    private static final String INITIAL_EXTENSION = "initial_extension";

    private final FileSystemService fileSystemService;
    private final GeneratorTypeId generatorTypeId;
    private final GeneratorIdService generatorIdService;

    @Override
    public String store(@NonNull Attachment attachment) {
        var attachmentId = defineAttachmentId(attachment);

        try {
            fileSystemService.uploadFile(attachmentId, attachment.getContent());

            var metadata = customizeMetadataKey(attachment.getMetadata());
            addDefaultMetadata(metadata, attachment);
            fileSystemService.addMetadata(attachmentId, metadata);
        } catch (IoFileUploadException ex) {
            throw new FileUploadException(attachmentId, ex);
        } catch (IoFileUploadMetadataException ex) {
            this.remove(attachmentId);

            throw new FileUploadMetadataException(attachmentId, ex);
        }

        return attachmentId;
    }

    @Override
    public boolean exists(@NonNull String id) {
        return fileSystemService.existsFile(id);
    }

    @Override
    public boolean remove(@NonNull String id) {
        return fileSystemService.removeFile(id);
    }

    @Override
    public Attachment findBy(@NonNull String id) {
        try {
            var fileContent = fileSystemService.readFile(id);
            var metadata = fileSystemService.readMetadata(id, format("%s:*", USER_METADATA_KEY));
            var filename = metadata.get(INITIAL_FILENAME);
            var extension = metadata.get(INITIAL_EXTENSION);

            return new FileAttachment(id, fileContent, metadata, filename, extension);
        } catch (IoFileReadException ex) {
            throw new FileReadException(id, ex);
        } catch (IoFileMetadataReadException ex) {
            throw new FileMetadataReadException(id, ex);
        }
    }

    private Map<String, String> customizeMetadataKey(Map<String, String> metadata) {
        return metadata.entrySet().stream()
                .collect(Collectors.toMap(k -> format("%s:%s", USER_METADATA_KEY, k.getKey()), Map.Entry::getValue));
    }

    private void addDefaultMetadata(Map<String, String> metadata, Attachment attachment) {
        metadata.put(format("%s:%s", USER_METADATA_KEY, INITIAL_FILENAME), attachment.getFilename());
        metadata.put(format("%s:%s", USER_METADATA_KEY, INITIAL_EXTENSION), attachment.getFileExtension());
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
