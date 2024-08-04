package com.chernov;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.nio.file.Path;

import static java.util.Optional.ofNullable;

/**
 * FIXME "с помощью файловой системы: в виде файлов в директориях и в виде zip архивов" а где здес настройка как будут хранится файлы?
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DirectoryFileStorageProperties {

    // FIXME Вроде бы одна строка а называется во множественном ччисле. Так же поудмать может проще Path тип использовать ане просто строку
    private String directories;

    // FIXME а для чего это? Мы предполагаем что directories может быть null?  И что нам даст пустая строка в этом случае?
    public Path getDirectories() {
        return ofNullable(this.directories)
                .map(Path::of)
                .orElseGet(() -> Path.of(""));
    }
}
