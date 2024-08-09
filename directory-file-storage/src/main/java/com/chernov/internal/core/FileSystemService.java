package com.chernov.internal.core;

import com.chernov.internal.exceptions.IoFileMetadataReadException;
import com.chernov.internal.exceptions.IoFileReadException;
import com.chernov.internal.exceptions.IoFileUploadException;
import com.chernov.internal.exceptions.IoFileUploadMetadataException;

import java.io.InputStream;
import java.util.Map;

public interface FileSystemService {

    long uploadFile(String filename, InputStream content) throws IoFileUploadException;

    void addMetadata(String filename, Map<String, String> metadata) throws IoFileUploadMetadataException;

    InputStream readFile(String filename) throws IoFileReadException;

    Map<String, String> readMetadata(String filename, String metadataKey) throws IoFileMetadataReadException;

    boolean existsFile(String filename);

    boolean removeFile(String filename);
}
