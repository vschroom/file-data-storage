package com.chernov.internal.core;

import com.chernov.internal.exceptions.FileCreateException;
import com.chernov.internal.exceptions.FileDeleteException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;

@RequiredArgsConstructor
public class FileSystemServiceImpl implements FileSystemService {

    private final Path directory;

    @Override
    public long uploadFile(@NonNull String filename, @NonNull InputStream content) throws IOException {
        try (var in = content) {
            return Files.copy(in, directory.resolve(filename));
        }
    }

    @Override
    public void addMetadata(@NonNull String filename, @NonNull Map<String, String> metadata) throws IOException {
        var resultPath = directory.resolve(filename);
        for (var meta : metadata.entrySet()) {
            Files.setAttribute(resultPath, meta.getKey(), meta.getValue().getBytes(UTF_8));
        }
    }

    @Override
    public InputStream readFile(@NonNull String filename) throws IOException {
        var resultPath = directory.resolve(filename);

        return Files.newInputStream(resultPath);
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
    public Map<String, String> readMetadata(@NonNull String filename, @NonNull String metadataKey) throws IOException {
        var resultPath = directory.resolve(filename);

        return Files.readAttributes(resultPath, metadataKey).entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> new String((byte[]) e.getValue(), Charset.defaultCharset())));
    }

    @Override
    public void createDirectoryIfNotExists(Path directory) {
        try {
            var directoryExists = Files.exists(directory);
            if (!directoryExists) {
                Files.createDirectory(directory);
            }
        } catch (IOException e) {
            throw new FileCreateException(directory.toString(), e);
        }
    }
}
