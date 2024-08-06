package com.chernov.internal.core;

import com.chernov.Attachment;
import com.chernov.FileAttachment;
import com.chernov.internal.api.InternalFileStorageApi;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.util.Optional.of;

@RequiredArgsConstructor
public class DirectoryFileStorage implements InternalFileStorageApi {

    private final FileSystemService fileSystemService;
    private final String customMetadataKey;

    @Override
    public String store(@NonNull Attachment attachment) {
        var attachmentId = attachment.getId();
        fileSystemService.uploadFile(attachmentId, attachment.getContent());

        var metadata = customizeMetadataKey(attachment.getMetadata());
        fileSystemService.addMetadata(attachmentId, metadata);

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
    public Optional<Attachment> findBy(@NonNull String id) {
        var fileContent = fileSystemService.readFile(id);
        var metadata = fileSystemService.readMetadata(id, format("%s:*", customMetadataKey));

        return of(new FileAttachment(id, fileContent, metadata));
    }

    private Map<String, String> customizeMetadataKey(Map<String, String> metadata) {
        return metadata.entrySet().stream()
                .collect(Collectors.toMap(k -> format("%s:%s", customMetadataKey, k.getKey()), Map.Entry::getValue));
    }
}
