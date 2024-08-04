package com.chernov.internal.core;

import com.chernov.Attachment;
import com.chernov.FileAttachment;
import com.chernov.internal.api.InternalFileStorageApi;
import lombok.RequiredArgsConstructor;

import java.nio.file.Path;
import java.util.Collection;

@RequiredArgsConstructor
public class DirectoryFileStorage implements InternalFileStorageApi {

    private final FileSystemService fileSystemService;

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
    public Attachment findBy(String id) {
        var fileContent = fileSystemService.readFile(toPath(id));
        var metadata = fileSystemService.readMetadata(toPath(id));

        return new FileAttachment(id, fileContent, metadata);
    }

    // FIXME для чего этот метод?
    private String joinUserMetadataKeys(Collection<String> metadataKeys) {
        return String.join(",", metadataKeys);
    }

    private Path toPath(String path) {
        return createDirectoriesByProperty().resolve(Path.of(path));
    }

    // FIXME так же как с минию, зачем нам на каждом бедном запросе проверять созданы ли рабочая диретория? Видимо есть какой то концептуальный пробел
    private Path createDirectoriesByProperty() {
        return fileSystemService.createDirectoriesByProperty();
    }
}
