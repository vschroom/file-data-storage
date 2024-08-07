package com.chernov;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class MainApp {

    public static void main(String[] args) throws IOException {
        // Choose impl, e.g. minio or directory
        var fileStorageApi = MinioStorageConfig.fileStorageApi();
//        var fileStorageApi = DirectoryStorageConfig.fileStorageApi();

        // store file attachment
        var fileId = "1";
        var id = fileStorageApi.store(
                new FileAttachment(fileId,
                        // FIXME Зачем в ByteArrayInputStream?
                        new ByteArrayInputStream(Files.readAllBytes(Path.of("helloworld.txt"))),
                        new HashMap<>(
                                Map.of(
                                "user_created", "user123",
                                "filename", "helloworld.txt")
                        ),
                        "helloworld",
                                FileExtension.TXT,
                        GeneratorTypeId.CUSTOM_GENERATOR,
                        new CustomGeneratorId()
                ));

        // find
        var attachment = fileStorageApi.findBy(id)
                .orElseThrow(() -> new IllegalArgumentException("attachment not found"));

        // download content
        try (var bw = new FileOutputStream("custom-dir\\test_response.txt");
             var content = attachment.getContent()) {
            bw.write(content.readAllBytes());
            bw.flush();
        }

        // check existence
        System.out.println(fileStorageApi.exists(id));
        // remove file
//        System.out.println(fileStorageApi.remove(id));
    }
}
