package com.chernov.internal.core;

import com.chernov.internal.exceptions.FileCreateException;
import com.chernov.internal.exceptions.FileDeleteException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

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

import static com.chernov.internal.domain.FileExtension.ZIP;
import static java.nio.charset.StandardCharsets.UTF_8;

@RequiredArgsConstructor
public class ZipFileSystemServiceImpl implements FileSystemService {

    private final Path directory;

    @Override
    public long uploadFile(@NonNull String filename, @NonNull InputStream content) throws IOException {
        var resultPath = resolveZipPath(filename).toString();
        try (var out = new ZipOutputStream(new FileOutputStream(resultPath));
             var in = content) {
            var zipEntry = new ZipEntry(filename);
            out.putNextEntry(zipEntry);

            return in.transferTo(out);
        }
    }

    @Override
    public void addMetadata(@NonNull String filename, @NonNull Map<String, String> metadata) throws IOException {
        var resultPath = resolveZipPath(filename);
        for (var meta : metadata.entrySet()) {
            Files.setAttribute(resultPath, meta.getKey(), meta.getValue().getBytes(UTF_8));
        }
    }

    @Override
    public InputStream readFile(@NonNull String filename) throws IOException {
        var resultPath = resolveZipPath(filename).toString();
        var zipFile = new ZipFile(resultPath, Charset.defaultCharset());
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

    }

    @Override
    public Map<String, String> readMetadata(@NonNull String filename, @NonNull String metadataKey) throws IOException {
        var resultPath = resolveZipPath(filename);

        return Files.readAttributes(resultPath, metadataKey).entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> new String((byte[]) e.getValue(), Charset.defaultCharset())));
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

    private Path resolveZipPath(String filename) {
        return directory.resolve(filename + ZIP.getValue());
    }
}
