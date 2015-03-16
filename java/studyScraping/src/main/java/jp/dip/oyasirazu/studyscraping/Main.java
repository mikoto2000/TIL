package jp.dip.oyasirazu.studyscraping;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Main
 */
public class Main {
    /**
     * Main Method
     */
    public static void main(String[] args) throws IOException {
        Document document = Jsoup.connect("http://www.google.co.jp").get();
        System.out.println(document.title());
    }
}
