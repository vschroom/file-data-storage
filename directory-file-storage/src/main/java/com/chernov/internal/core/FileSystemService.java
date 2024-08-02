package com.chernov.internal.core;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.Map;

interface FileSystemService {

    Path uploadFile(Path path, InputStream content);

    void addMetadata(Path path, Map<String, String> metadata);

    InputStream readFile(Path path);

    Map<String, String> readMetadata(Path path, String metadataKeys);

    boolean existsFile(Path path);

    boolean removeFile(Path path);

    Path createDirectoriesByProperty();
}
