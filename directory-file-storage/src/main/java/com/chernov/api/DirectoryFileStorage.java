package com.chernov.api;

import com.chernov.Attachment;
import com.chernov.core.FileSystemService;
import com.chernov.internal.api.InternalFileStorageApi;
import com.chernov.internal.impl.FileAttachment;

import java.nio.file.Path;
import java.util.Collection;

public record DirectoryFileStorage(FileSystemService fileSystemService) implements InternalFileStorageApi {

    @Override
    public void store(Attachment attachment) {
        var path = fileSystemService.uploadFile(toPath(attachment.getId()), attachment.getContent());
        fileSystemService.addMetadata(path, attachment.getMetadata());
    }

    @Override
    public boolean exists(String id) {
        return fileSystemService.existsFile(toPath(id));
    }

    @Override
    public boolean remove(String id) {
        return fileSystemService.removeFile(toPath(id));
    }

    @Override
    public Attachment findBy(String id, Collection<String> metadataKeys) {
        var attributes = joinUserMetadataKeys(metadataKeys);
        var fileContent = fileSystemService.readFile(toPath(id));
        var metadata = fileSystemService.readMetadata(toPath(id), attributes);

        return new FileAttachment(id, fileContent, metadata, null);
    }

    private String joinUserMetadataKeys(Collection<String> metadataKeys) {
        return String.join(",", metadataKeys);
    }

    private Path toPath(String path) {
        return createDirectoriesByProperty().resolve(Path.of(path));
    }

    private Path createDirectoriesByProperty() {
        return fileSystemService.createDirectoriesByProperty();
    }
}