package jp.dip.oyasirazu.studywalkfiletree;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Glob
 */
public class Glob {
    public static void main(String[] args) throws IOException {
        // 第一引数で指定されたディレクトリ以下から
        // 第二引数で指定された glob にマッチするファイルを抽出
        Path target = Paths.get(args[0]);

        PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:" + args[1]);
        Files.walkFileTree(target,
                new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(
                            Path file,
                            BasicFileAttributes attr) throws IOException {
                        if (pathMatcher.matches(file)) {
                            System.out.println(file.toString());
                        }
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult preVisitDirectory(
                            Path file,
                            BasicFileAttributes attr) throws IOException {
                        if (pathMatcher.matches(file)) {
                            System.out.println(file.toString());
                        }
                        return FileVisitResult.CONTINUE;
                    }
                });
    }
}

