package dev.mikoto2000.study.sns.mastodon;

import java.util.List;

import org.jsoup.Jsoup;
import org.mastodon4j.core.MyMastodonClient;
import org.mastodon4j.core.api.MastodonApi;
import org.mastodon4j.core.api.Timelines;
import org.mastodon4j.core.api.entities.AccessToken;
import org.mastodon4j.core.api.entities.Status;

/**
 * Hello world!
 */
public class App {

  private static final String INSTANCE = "https://social.mikutter.hachune.net";
  private static final String ACCESS_TOKEN = "v09K0bA1oY0RYXhfKEnfl0WYYPDsu3xoD6h8rAZuT80";

  public static void main(String[] args) {

    // クライアント作成
    MastodonApi api = MyMastodonClient.create(INSTANCE, AccessToken.create(ACCESS_TOKEN));

    Timelines timelines = api.timelines();
    List<Status> homeTimeline = timelines.home();

    for (Status status : homeTimeline) {
      var text = Jsoup.parse(status.content()).text();
      if (text != null && !text.equals("")) {
        System.out.println(text);
      }
    }

  }
}
