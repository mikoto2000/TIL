package jp.dip.oyasirazu.studywalkfiletree;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Main
 */
public class Main {
    public static void main(String[] args) throws IOException {
        // 第一引数で指定されたディレクトリ以下を走査
        Path target = Paths.get(args[0]);

        Files.walkFileTree(target,
                new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(
                            Path file,
                            BasicFileAttributes attr) throws IOException {
                        System.out.println(file.toString());
                        return FileVisitResult.CONTINUE;
                    }
                });
    }
}
