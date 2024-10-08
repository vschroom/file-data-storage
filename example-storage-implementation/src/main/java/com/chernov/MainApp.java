package com.chernov;

import com.chernov.impl.FileAttachment;
import com.chernov.internal.domain.FileExtension;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class MainApp {

    public static void main(String[] args) throws IOException {
        // Choose impl, e.g. minio or directory
//        var fileStorageApi = MinioStorageConfig.fileStorageApi();
        var fileStorageApi = DirectoryStorageConfig.fileStorageApi();

        // store file attachment
        var fileId = "1";
        var id = fileStorageApi.store(
                new FileAttachment(fileId,
                        Files.newInputStream(Path.of("helloworld.txt")),
                        new HashMap<>(
                                Map.of(
                                        "user_created", "user123",
                                        "filename", "helloworld.txt")
                        ),
                        "helloworld",
                        FileExtension.TXT.getValue()
                ));

        // find
        var attachment = fileStorageApi.findBy(id);

        // download content
        try (var bw = new FileOutputStream("user-dir\\test_response.txt");
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
