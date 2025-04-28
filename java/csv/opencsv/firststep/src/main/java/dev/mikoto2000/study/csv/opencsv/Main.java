package dev.mikoto2000.study.csv.opencsv;

import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        List<User> beans = new CsvToBeanBuilder<User>(new FileReader("testdata/test.csv"))
                .withType(User.class).build().parse();

        for (User user : beans) {
            System.out.println(user);
        }
    }
}