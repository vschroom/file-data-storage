package com.chernov.internal.core;

import com.chernov.DirectoryFileStorageProperties;
import com.chernov.internal.exceptions.*;
import lombok.NonNull;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;

public class FileSystemServiceImpl implements FileSystemService {

    private final Path directory;

    public FileSystemServiceImpl(DirectoryFileStorageProperties properties) {
        this.directory = createDirectoryIfNotExists(properties.getDirectory());
    }

    @Override
    public long uploadFile(@NonNull String filename, @NonNull InputStream content) throws IoFileUploadException {
        try (var in = content) {
            return Files.copy(in, directory.resolve(filename));
        } catch (IOException e) {
            throw new IoFileUploadException(filename, e);
        }
    }

    @Override
    public void addMetadata(@NonNull String filename, @NonNull Map<String, String> metadata) throws IoFileUploadMetadataException {
        var resultPath = directory.resolve(filename);
        for (var meta : metadata.entrySet()) {
            try {
                Files.setAttribute(resultPath, meta.getKey(), meta.getValue().getBytes(UTF_8));
            } catch (IOException e) {
                throw new IoFileUploadMetadataException(resultPath.toString(), e);
            }
        }
    }

    @Override
    public InputStream readFile(@NonNull String filename) throws IoFileReadException {
        var resultPath = directory.resolve(filename);
        try {
            return Files.newInputStream(resultPath);
        } catch (IOException ex) {
            throw new IoFileReadException(resultPath.toString(), ex);
        }
    }

    @Override
    public boolean existsFile(@NonNull String filename) {
        return Files.exists(directory.resolve(filename));
    }

    @Override
    public boolean removeFile(@NonNull String filename) {
        try {
            return Files.deleteIfExists(directory.resolve(filename));
        } catch (IOException e) {
            throw new FileDeleteException(filename, e);
        }
    }

    @Override
    public Map<String, String> readMetadata(@NonNull String filename, @NonNull String metadataKey) throws IoFileMetadataReadException {
        var resultPath = directory.resolve(filename);
        try {
            return Files.readAttributes(resultPath, metadataKey).entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, e -> new String((byte[]) e.getValue(), Charset.defaultCharset())));
        } catch (IOException e) {
            throw new IoFileMetadataReadException(resultPath.toString(), e);
        }
    }

    private Path createDirectoryIfNotExists(Path directory) {
        try {
            var directoryExists = Files.exists(directory);
            if (!directoryExists) {
                return Files.createDirectory(directory);
            }

            return directory;
        } catch (IOException e) {
            throw new FileCreateException(directory.toString(), e);
        }
    }
}
