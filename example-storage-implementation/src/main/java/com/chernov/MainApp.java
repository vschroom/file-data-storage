package com.chernov;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class MainApp {

    public static void main(String[] args) throws IOException {
        // Choose impl, e.g. minio or directory
        var fileStorageApi = MinioStorageConfig.fileStorageApi();
//        var fileStorageApi = DirectoryStorageConfig.fileStorageApi();

        // store file attachment
        var fileId = "b8f7c0a6-0daf-4307-a124-39e01be794ed";
        fileStorageApi.store(
                new FileAttachment(fileId,
                        new ByteArrayInputStream(Files.readAllBytes(Path.of("helloworld.pdf"))),
                        Map.of(
                                "user_created", "user123",
                                "filename", "helloworld.txt")
                ));

        // find
        var attachment = fileStorageApi.findBy(fileId);

        // download content
        try (var bw = new FileOutputStream("test_response.pdf")) {
            bw.write(attachment.getContent().readAllBytes());
            bw.flush();
        }

        // check existence
        System.out.println(fileStorageApi.exists(fileId));
        // remove file
        System.out.println(fileStorageApi.remove(fileId));
    }
}
