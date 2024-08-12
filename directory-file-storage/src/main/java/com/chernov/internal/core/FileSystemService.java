package com.chernov.internal.core;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Map;

public interface FileSystemService {

    long uploadFile(String filename, InputStream content) throws IOException;

    void addMetadata(String filename, Map<String, String> metadata) throws IOException;

    InputStream readFile(String filename) throws IOException;

    Map<String, String> readMetadata(String filename, String metadataKey) throws IOException;

    boolean existsFile(String filename);

    boolean removeFile(String filename);

    void createDirectoryIfNotExists(Path directory);
}
