package com.chernov.internal.core;

import com.chernov.Attachment;
import com.chernov.FileAttachment;
import com.chernov.FileExtension;
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

    private static final String USER_METADATA_KEY = "user";
    private static final String INITIAL_FILENAME = "initial_filename";
    private static final String INITIAL_EXTENSION = "initial_extension";
    private final FileSystemService fileSystemService;

    @Override
    public String store(@NonNull Attachment attachment) {
        var attachmentId = attachment.getId();
        fileSystemService.uploadFile(attachmentId, attachment.getContent());

        var metadata = customizeMetadataKey(attachment.getMetadata());
        addDefaultMetadata(metadata, attachment);
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
        var metadata = fileSystemService.readMetadata(id, format("%s:*", USER_METADATA_KEY));
        var filename = metadata.get(INITIAL_FILENAME);
        var extension = FileExtension.parse(metadata.get(INITIAL_EXTENSION));

        return of(new FileAttachment(id, fileContent, metadata, filename, extension));
    }

    private Map<String, String> customizeMetadataKey(Map<String, String> metadata) {
        return metadata.entrySet().stream()
                .collect(Collectors.toMap(k -> format("%s:%s", USER_METADATA_KEY, k.getKey()), Map.Entry::getValue));
    }

    private void addDefaultMetadata(Map<String, String> metadata, Attachment attachment) {
        metadata.put(format("%s:%s", USER_METADATA_KEY, INITIAL_FILENAME), attachment.getFilename());
        metadata.put(format("%s:%s", USER_METADATA_KEY, INITIAL_EXTENSION), attachment.getFileExtension().getValue());
    }
}
