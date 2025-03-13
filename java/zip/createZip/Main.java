import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

  // 圧縮ターゲットファイル
  private static final String[] TARGET_FILES = new String[]{"resources/test1.txt", "resources/dir/test2.txt", "resources/test3.txt",};

  // 圧縮ファイル出力先
  private static final String ZIP_FILE_PATH = "test.zip";

  public static void main(String[] args) throws Exception {

    try (var zip = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(ZIP_FILE_PATH)))) {
      for (var filePathStr : TARGET_FILES) {
        var filePath = Paths.get(filePathStr);

        // 次のエントリーを設定
        zip.putNextEntry(new ZipEntry(filePath.getFileName().toString()));

        // 設定したエントリーにファイル情報を流し込む
        zip.write(Files.readAllBytes(filePath));
      }
    }
  }
}
