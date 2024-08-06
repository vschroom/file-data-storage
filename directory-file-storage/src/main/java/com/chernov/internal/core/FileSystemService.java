package com.chernov.internal.core;

import java.io.InputStream;
import java.util.Map;

public interface FileSystemService {

    long uploadFile(String filename, InputStream content);

    void addMetadata(String filename, Map<String, String> metadata);

    InputStream readFile(String filename);

    Map<String, String> readMetadata(String filename, String metadataKey);

    boolean existsFile(String filename);

    boolean removeFile(String filename);
}
