package jp.dip.oyasirazu.studyzip;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Main
 */
public class Main {

    private static int BUFFER_SIZE = 2048;

    public static void main(String[] args) throws IOException {
        unzip(args[0], args[1]);
    }

    private static void unzip(String zipFilePath, String outDir) throws IOException {
        // Zip ファイルから ZipEntry を一つずつ取り出し、ファイルに保存していく。
        ZipFile zipFile = new ZipFile(zipFilePath);

        zipFile.stream().forEach( entry -> {
            Path entryPath = Paths.get(outDir, entry.getName());
            if (entry.isDirectory()) {
                System.out.println(entry.getName());
                try {
                    Files.createDirectories(entryPath);
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            } else {
                System.out.println(entry.getName());
                try (InputStream is = zipFile.getInputStream(entry)) {
                    outputFile(entryPath, is);
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        });
    }

    private static void outputFile(Path outPath , InputStream is)
            throws IOException {

        try (
            OutputStream os = Files.newOutputStream(outPath);
        ) {

            byte[] buffer = new byte[BUFFER_SIZE];

            while(true) {
                int length = is.read(buffer);

                // 最後まで読み込んだら終了する
                if (length == -1) {
                    break;
                }

                os.write(buffer, 0, length);
            }
        }
    }
}
