package dev.mikoto2000.study.csv.opencsv;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, CsvValidationException {
        CSVReader reader = new CSVReader(new FileReader("testdata/test.csv"));

        String[] line;
        while ((line = reader.readNext()) != null) {
            for (String str : line) {
                System.out.print(str.strip() + " ");
            }
            System.out.println();
        }
    }
}