package by.teachmeskills.crud.util;

import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.Value;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Map;

@Value
public class MySqlScriptsVisitor implements FileVisitor<Path> {
    @NonNull Map<String, String> queries;

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
        return FileVisitResult.CONTINUE;
    }

    @Override
    @SneakyThrows(IOException.class)
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        if (file.toString().endsWith(".sql")) {
            String fileName = file.getFileName().toString().trim().replaceAll("\\.sql", "");
            List<String> content = Files.readAllLines(file);
            StringBuilder builder = new StringBuilder();
            for (String line : content) builder.append(line).append('\n');
            queries.put(fileName, builder.toString());
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
        return FileVisitResult.CONTINUE;
    }
}