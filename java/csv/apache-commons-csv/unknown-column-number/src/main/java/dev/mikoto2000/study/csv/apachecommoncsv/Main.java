package dev.mikoto2000.study.csv.apachecommoncsv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class Main {

    public static final String[] HEADERS = new String[]{"番号", "名前", "メールアドレス"};

    public static void main(String[] args) throws IOException {

        try (Reader in = new FileReader("testdata/test.csv")) {

            for (String header : HEADERS) {
                System.out.print(header + " ");
            }
            System.out.println();

            Iterable<CSVRecord> records = CSVFormat.RFC4180.builder().setHeader(HEADERS).build().parse(in);
            for (CSVRecord record : records) {
                for (int i = 0; i < record.size(); i++) {
                    System.out.print(record.get(i) + " ");
                }
                System.out.println();
            }
        }
    }
}