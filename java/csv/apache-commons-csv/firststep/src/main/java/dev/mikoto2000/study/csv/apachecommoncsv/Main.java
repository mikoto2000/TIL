package dev.mikoto2000.study.csv.apachecommoncsv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class Main {
    public static void main(String[] args) throws IOException {
        Reader in = new FileReader("testdata/test.csv");
        Iterable<CSVRecord> records = CSVFormat.RFC4180.builder().setHeader("番号", "名前", "メールアドレス").build().parse(in);
        for (CSVRecord record : records) {
            String number = record.get("番号");
            String name = record.get("名前");
            String mailAddress = record.get("メールアドレス");

            System.out.printf("%s,%s,%s\n",number,name,mailAddress);
        }
    }
}