package jp.dip.oyasirazu.studywalkfiletree;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

/**
 * Main
 */
public class Main {
    public static void main(String[] args) throws IOException {
        // 第一引数で指定されたディレクトリ以下を走査
        Path target = Paths.get(args[0]);

        List<Path> paths = new ArrayList<>();
        Files.walkFileTree(target,
                new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(
                            Path file,
                            BasicFileAttributes attr) throws IOException {
                        paths.add(file);
                        return FileVisitResult.CONTINUE;
                    }
                });

        System.out.println(paths);

        int fileContentDepth = 2;
        Path backupBasePath = Paths.get("./testdata/bkup");

        for (var path : paths) {
          // path をルートからの相対パスに変更
          Path relativePathFromRoot = path.subpath(path.getNameCount() - fileContentDepth, path.getNameCount());

          // バックアップ先計算
          Path backupPath = backupBasePath.resolve(relativePathFromRoot);

          // ディレクトリ作成
          Files.createDirectories(backupPath.subpath(0, backupPath.getNameCount() - 1));

          // バックアップ先表示
          System.out.println(backupPath);

          // バックアップ
          Files.copy(path, backupPath);
        }
    }
}
