package com.chernov.internal.core;

import com.chernov.DirectoryFileStorageProperties;
import com.chernov.internal.exceptions.*;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.nio.charset.StandardCharsets.UTF_8;

@RequiredArgsConstructor
public class FileSystemServiceImpl implements FileSystemService {

    private final DirectoryFileStorageProperties properties;

    public Path uploadFile(Path path, InputStream content) {
        // FIXME валидация для этого класса излишняя, он должен тупо работать с системным файловым хранилищем
        validateExistence(path);

        try (var in = content) {
            Files.copy(in, path);

            return path;
        } catch (IOException e) {
            throw new FileUploadException(path.toString(), e);
        }
    }

    public void addMetadata(Path path, Map<String, String> metadata) {
        metadata.forEach((key, value) -> addMetadata(path, key, value));
    }

    public InputStream readFile(Path path) {
        try {
            return Files.newInputStream(path);
        } catch (IOException ex) {
            throw new FileReadException(path.toString());
        }
    }

    public Map<String, String> readMetadata(Path path) {
        try {
            // FIXME хардкод
            return Files.readAttributes(path, "user:*").entrySet().stream()
                    // FIXME кодировку байт тоже нужно указать
                    .collect(Collectors.toMap(Map.Entry::getKey, e -> new String((byte[]) e.getValue())));
        } catch (IOException e) {
            throw new FileMetadataReadException(path.toString(), e);
        }
    }

    @Override
    public boolean existsFile(Path path) {
        return Files.exists(path);
    }

    @Override
    public boolean removeFile(Path path) {
        try {
            return Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new FileDeleteException(path.toString(), e);
        }
    }

    private void addMetadata(Path path, String key, String value) {
        // FIXME хардкод
        var userKey = format("user:%s", key);
        try {
            Files.setAttribute(path, userKey, value.getBytes(UTF_8));
        } catch (IOException e) {
            throw new FileUploadMetadataException(path.toString(), e);
        }
    }

    private void validateExistence(Path path) {
        var existsFile = existsFile(path);
        if (existsFile) {
            throw new FileAlreadyExistsException(path.toString());
        }
    }

    public Path createDirectoriesByProperty() {
        var directories = properties.getDirectories();
        try {
            return Files.createDirectories(directories);
        } catch (IOException e) {
            throw new FileCreateDirectoryException(directories.toString(), e);
        }
    }
}
