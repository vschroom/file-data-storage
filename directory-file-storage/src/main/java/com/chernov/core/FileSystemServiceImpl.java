package com.chernov.core;

import com.chernov.exceptions.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;

public record FileSystemServiceImpl(DirectoryFileStorageProperties properties) implements FileSystemService {

    public Path uploadFile(Path path, InputStream content) {
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

    public Map<String, String> readMetadata(Path path, String metadataKeys) {
        var attributes = "user:%s".formatted(metadataKeys);
        try {
            return Files.readAttributes(path, attributes).entrySet().stream()
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
        var userKey = "user:%s".formatted(key);
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
