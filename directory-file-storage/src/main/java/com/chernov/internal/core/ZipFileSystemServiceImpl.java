package com.chernov.internal.core;

import com.chernov.DirectoryFileStorageProperties;
import com.chernov.internal.exceptions.*;
import lombok.NonNull;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import static com.chernov.FileExtension.ZIP;
import static java.nio.charset.StandardCharsets.UTF_8;

public class ZipFileSystemServiceImpl implements FileSystemService {

    private final Path directory;

    public ZipFileSystemServiceImpl(DirectoryFileStorageProperties properties) {
        this.directory = createDirectoryIfNotExists(properties.getDirectory());
    }

    @Override
    public long uploadFile(@NonNull String filename, @NonNull InputStream content) {
        var resultPath = resolveZipPath(filename).toString();
        try (var out = new ZipOutputStream(new FileOutputStream(resultPath));
             var in = content) {
            var zipEntry = new ZipEntry(filename);
            out.putNextEntry(zipEntry);

            return in.transferTo(out);
        } catch (IOException e) {
            throw new FileUploadException(filename, e);
        }
    }

    @Override
    public void addMetadata(@NonNull String filename, @NonNull Map<String, String> metadata) {
        metadata.forEach((key, value) -> addMetadata(
                resolveZipPath(filename), key, value));
    }

    @Override
    public InputStream readFile(@NonNull String filename) {
        try {
            var zipFile = new ZipFile(resolveZipPath(filename).toString(), Charset.defaultCharset());
            var zipInputStream = zipFile.getInputStream(zipFile.entries().nextElement());

            return new InputStream() {

                @Override
                public int read() throws IOException {
                    return zipInputStream.read();
                }

                @Override
                public void close() throws IOException {
                    zipInputStream.close();
                    zipFile.close();
                }
            };
        } catch (IOException ex) {
            throw new FileReadException(filename);
        }
    }

    @Override
    public Map<String, String> readMetadata(@NonNull String filename, @NonNull String metadataKey) {
        try {
            return Files.readAttributes(resolveZipPath(filename), metadataKey).entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, e -> new String((byte[]) e.getValue(), Charset.defaultCharset())));
        } catch (IOException e) {
            throw new FileMetadataReadException(filename, e);
        }
    }

    @Override
    public boolean existsFile(@NonNull String filename) {
        return Files.exists(resolveZipPath(filename));
    }

    @Override
    public boolean removeFile(@NonNull String filename) {
        try {
            return Files.deleteIfExists(resolveZipPath(filename));
        } catch (IOException e) {
            throw new FileDeleteException(filename, e);
        }
    }

    private Path createDirectoryIfNotExists(Path directory) {
        try {
            var directoryExists = Files.exists(directory);

            return !directoryExists
                    ? Files.createDirectory(directory)
                    : directory;
        } catch (IOException e) {
            throw new FileCreateException(directory.toString(), e);
        }
    }

    private void addMetadata(Path path, String key, String value) {
        try {
            Files.setAttribute(path, key, value.getBytes(UTF_8));
        } catch (IOException e) {
            throw new FileUploadMetadataException(path.toString(), e);
        }
    }

    private Path resolveZipPath(String filename) {
        return directory.resolve(filename + ZIP.getValue());
    }
}
